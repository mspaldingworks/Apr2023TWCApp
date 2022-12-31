package com.mad.e.dec2022twcapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.google.samples.apps.nowinandroid.core.database.dao.upsert
import com.mad.e.twccomposeplanner.core.database.model.SessionEntity
import com.mad.e.dec2022twcapp.core.database.model.PopulatedSession
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [SessionEntity] and [Episode] access
 */
@Dao
interface SessionDao {
    @Query(value = "SELECT * FROM episodes")
    fun getEpisodesStream(): Flow<List<PopulatedSession>>

    /**
     * Inserts [episodeEntities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreEpisodes(episodeEntities: List<SessionEntity>): List<Long>

    /**
     * Updates [entities] in the db that match the primary key, and no-ops if they don't
     */
    @Update
    suspend fun updateEpisodes(entities: List<SessionEntity>)

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Transaction
    suspend fun upsertEpisodes(entities: List<SessionEntity>) = upsert(
        items = entities,
        insertMany = ::insertOrIgnoreEpisodes,
        updateMany = ::updateEpisodes
    )

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM episodes
            WHERE id in (:ids)
        """
    )
    suspend fun deleteEpisodes(ids: List<String>)
}
