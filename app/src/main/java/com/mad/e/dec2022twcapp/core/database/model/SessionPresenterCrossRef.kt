package com.mad.e.dec2022twcapp.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.mad.e.twccomposeplanner.core.database.model.PresenterEntity

/**
 * Cross reference for many to many relationship between [SessionEntity] and [PresenterEntity]
 */
@Entity(
    tableName = "sessions_presenters",
    primaryKeys = ["session_id", "presenter_id"],
    foreignKeys = [
        ForeignKey(
            entity = SessionEntity::class,
            parentColumns = ["id"],
            childColumns = ["session_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PresenterEntity::class,
            parentColumns = ["id"],
            childColumns = ["presenter_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [
        Index(value = ["session_id"]),
        Index(value = ["presenter_id"]),
    ],
)
data class SessionPresenterCrossRef(
    @ColumnInfo(name = "session_id")
    val sessionId: String,
    @ColumnInfo(name = "presenter_id")
    val presenterId: String,
)
