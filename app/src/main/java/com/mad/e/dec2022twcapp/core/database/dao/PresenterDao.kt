package com.mad.e.dec2022twcapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.google.samples.apps.nowinandroid.core.database.dao.upsert
import com.mad.e.twccomposeplanner.core.database.model.PresenterEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [PresenterEntity] access
 */
@Dao
interface PresenterDao {
    @Query(
        value = """
        SELECT * FROM authors
        WHERE id = :authorId
    """
    )
    fun getPresenterEntityStream(authorId: String): Flow<PresenterEntity>

    @Query(value = "SELECT * FROM authors")
    fun getPresenterEntitiesStream(): Flow<List<PresenterEntity>>

    /**
     * Inserts [authorEntities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreAuthors(authorEntities: List<PresenterEntity>): List<Long>

    /**
     * Updates [entities] in the db that match the primary key, and no-ops if they don't
     */
    @Update
    suspend fun updateAuthors(entities: List<PresenterEntity>)

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Transaction
    suspend fun upsertAuthors(entities: List<PresenterEntity>) = upsert(
        items = entities,
        insertMany = ::insertOrIgnoreAuthors,
        updateMany = ::updateAuthors
    )

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM authors
            WHERE id in (:ids)
        """
    )
    suspend fun deleteAuthors(ids: List<String>)
}
