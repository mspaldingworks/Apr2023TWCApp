package com.mad.e.twccomposeplanner.core.data.repository

import com.mad.e.dec2022twcapp.core.data.model.asEntity
import com.mad.e.dec2022twcapp.core.data.repository.Synchronizer
import com.mad.e.dec2022twcapp.core.data.repository.TopicsRepository
import com.mad.e.dec2022twcapp.core.data.repository.changeListSync
import com.mad.e.dec2022twcapp.core.database.dao.TopicDao
import com.mad.e.twccomposeplanner.core.database.model.TopicEntity
import com.mad.e.dec2022twcapp.core.database.model.asExternalModel
import com.mad.e.dec2022twcapp.core.datastore.ChangeListVersions
import com.mad.e.dec2022twcapp.core.model.Topic
import com.mad.e.twccomposeplanner.network.model.NetworkTopic
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Disk storage backed implementation of the [TopicsRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstTopicsRepository @Inject constructor(
    private val topicDao: TopicDao,
    private val network: TwcNetworkDataSource,
    private val niaPreferences: TwcPreferencesDataSource,
) : TopicsRepository {

    override fun getTopicsStream(): Flow<List<Topic>> =
        topicDao.getTopicEntitiesStream()
            .map { it.map(TopicEntity::asExternalModel) }

    override fun getTopic(id: String): Flow<Topic> =
        topicDao.getTopicEntity(id).map { it.asExternalModel() }

    override suspend fun setFollowedTopicIds(followedTopicIds: Set<String>) =
        niaPreferences.setFollowedTopicIds(followedTopicIds)

    override suspend fun toggleFollowedTopicId(followedTopicId: String, followed: Boolean) =
        niaPreferences.toggleFollowedTopicId(followedTopicId, followed)

    override fun getFollowedTopicIdsStream() = niaPreferences.followedTopicIds

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.changeListSync(
            versionReader = ChangeListVersions::topicVersion,
            changeListFetcher = { currentVersion ->
                network.getTopicChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(topicVersion = latestVersion)
            },
            modelDeleter = topicDao::deleteTopics,
            modelUpdater = { changedIds ->
                val networkTopics = network.getTopics(ids = changedIds)
                topicDao.upsertTopics(
                    entities = networkTopics.map(NetworkTopic::asEntity)
                )
            }
        )
}
