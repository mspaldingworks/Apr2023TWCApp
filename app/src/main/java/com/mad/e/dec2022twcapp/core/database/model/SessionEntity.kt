package com.mad.e.dec2022twcapp.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

/**
 * Defines an Twc episode.
 * It is a parent in a 1 to many relationship with [NewsResourceEntity]
 */
@Entity(
    tableName = "sessions",
)
data class SessionEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    @ColumnInfo(name = "publish_date")
    val publishDate: Instant,
    @ColumnInfo(name = "alternate_video")
    val alternateVideo: String?,
    @ColumnInfo(name = "alternate_audio")
    val alternateAudio: String?,
)
