package com.mad.e.dec2022twcapp.core.data.repository

import com.mad.e.dec2022twcapp.core.data.model.asEntity
import com.mad.e.dec2022twcapp.core.database.dao.PresenterDao
import com.mad.e.twccomposeplanner.core.database.model.PresenterEntity
import com.mad.e.dec2022twcapp.core.database.model.asExternalModel
import com.mad.e.dec2022twcapp.core.datastore.ChangeListVersions
import com.mad.e.twccomposeplanner.core.model.Presenter
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Disk storage backed implementation of the [PresentersRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstPresentersRepository @Inject constructor(
    private val presenterDao: PresenterDao,
    private val twcPreferences: TwcPreferencesDataSource,
    private val network: TwcNetworkDataSource,

    ) : PresentersRepository {

    override fun getPresenterStream(id: String): Flow<Presenter> =
        presenterDao.getPresenterEntityStream(id).map {
            it.asExternalModel()
        }

    override fun getPresentersStream(): Flow<List<Presenter>> =
        presenterDao.getPresenterEntitiesStream()
            .map { it.map(PresenterEntity::asExternalModel) }

    override suspend fun setFollowedPresenterIds(followedPresenterIds: Set<String>) =
        twcPreferences.setFollowedPresenterIds(followedPresenterIds)

    override suspend fun toggleFollowedPresenterId(followedPresenterId: String, followed: Boolean) =
        twcPreferences.toggleFollowedPresenterId(followedPresenterId, followed)

    override fun getFollowedPresenterIdsStream(): Flow<Set<String>> = twcPreferences.followedpresenterIds

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.changeListSync(
            versionReader = ChangeListVersions::presenterVersion,
            changeListFetcher = { currentVersion ->
                network.getPresenterChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(presenterVersion = latestVersion)
            },
            modelDeleter = presenterDao::deleteAuthors,
            modelUpdater = { changedIds ->
                val networkPresenters = network.getPresenters(ids = changedIds)
                presenterDao.upsertAuthors(
                    entities = networkPresenters.map(NetworkPresenter::asEntity)
                )
            }
        )
}
