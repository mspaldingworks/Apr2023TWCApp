package com.mad.e.twccomposeplanner.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mad.e.twccomposeplanner.core.model.Presenter

/**
 * Defines an author for either an [SessionEntity] or [NewsResourceEntity].
 * It has a many to many relationship with both entities
 */
@Entity(
    tableName = "authors",
)
data class PresenterEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(defaultValue = "")
    val twitter: String,
    @ColumnInfo(name = "medium_page", defaultValue = "")
    val mediumPage: String,
    @ColumnInfo(defaultValue = "")
    val bio: String,
)

fun PresenterEntity.asExternalModel() = Presenter(
    id = id,
    name = name,
    imageUrl = imageUrl,
    twitter = twitter,
    mediumPage = mediumPage,
    bio = bio,
)
