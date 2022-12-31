package com.mad.e.dec2022twcapp.core.data.repository

import com.mad.e.dec2022twcapp.core.data.model.*
import com.mad.e.dec2022twcapp.core.database.dao.PresenterDao
import com.mad.e.dec2022twcapp.core.database.dao.SessionDao
import com.mad.e.dec2022twcapp.core.database.dao.TopicDao
import com.mad.e.twccomposeplanner.core.database.model.PresenterEntity
import com.mad.e.twccomposeplanner.core.database.model.SessionEntity
import com.mad.e.twccomposeplanner.core.database.model.PopulatedNewsResource
import com.mad.e.twccomposeplanner.core.database.model.TopicEntity
import com.mad.e.dec2022twcapp.core.database.model.asExternalModel
import com.mad.e.dec2022twcapp.core.datastore.ChangeListVersions
import com.mad.e.twccomposeplanner.core.model.Session
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Disk storage backed implementation of the [SessionRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstSessionsRepository @Inject constructor(
    private val sessionDao: SessionDao,
    private val presenterDao: PresenterDao,
    private val topicDao: TopicDao,
    private val network: TwcNetworkDataSource,
) : SessionRepository {

    override fun getSessionsStream(): Flow<List<Session>> =
        newsResourceDao.getNewsResourcesStream()
            .map { it.map(PopulatedNewsResource::asExternalModel) }

    override fun getSessionsStream(
        filterAuthorIds: Set<String>,
        filterTopicIds: Set<String>
    ): Flow<List<Session>> = newsResourceDao.getNewsResourcesStream(
        filterAuthorIds = filterAuthorIds,
        filterTopicIds = filterTopicIds
    )
        .map { it.map(PopulatedNewsResource::asExternalModel) }

    override suspend fun syncWith(synchronizer: Synchronizer) =
        synchronizer.changeListSync(
            versionReader = ChangeListVersions::newsResourceVersion,
            changeListFetcher = { currentVersion ->
                network.getNewsResourceChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(newsResourceVersion = latestVersion)
            },
            modelDeleter = newsResourceDao::deleteNewsResources,
            modelUpdater = { changedIds ->
                val networkNewsResources = network.getNewsResources(ids = changedIds)

                // Order of invocation matters to satisfy id and foreign key constraints!

                topicDao.insertOrIgnoreTopics(
                    topicEntities = networkNewsResources
                        .map(NetworkNewsResource::topicEntityShells)
                        .flatten()
                        .distinctBy(TopicEntity::id)
                )
                presenterDao.insertOrIgnoreAuthors(
                    authorEntities = networkNewsResources
                        .map(NetworkNewsResource::authorEntityShells)
                        .flatten()
                        .distinctBy(PresenterEntity::id)
                )
                sessionDao.insertOrIgnoreEpisodes(
                    episodeEntities = networkNewsResources
                        .map(NetworkNewsResource::episodeEntityShell)
                        .distinctBy(SessionEntity::id)
                )
                newsResourceDao.upsertNewsResources(
                    newsResourceEntities = networkNewsResources
                        .map(NetworkNewsResource::asEntity)
                )
                newsResourceDao.insertOrIgnoreTopicCrossRefEntities(
                    newsResourceTopicCrossReferences = networkNewsResources
                        .map(NetworkNewsResource::topicCrossReferences)
                        .distinct()
                        .flatten()
                )
                newsResourceDao.insertOrIgnoreAuthorCrossRefEntities(
                    newsResourceAuthorCrossReferences = networkNewsResources
                        .map(NetworkNewsResource::authorCrossReferences)
                        .distinct()
                        .flatten()
                )
            }
        )
}
