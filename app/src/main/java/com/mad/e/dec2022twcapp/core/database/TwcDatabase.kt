package com.mad.e.dec2022twcapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mad.e.dec2022twcapp.core.database.dao.PresenterDao
import com.mad.e.dec2022twcapp.core.database.dao.SessionDao
import com.mad.e.dec2022twcapp.core.database.dao.TopicDao
import com.mad.e.dec2022twcapp.core.database.model.SessionEntity
import com.mad.e.dec2022twcapp.core.database.model.TopicEntity
import com.mad.e.dec2022twcapp.core.database.model.SessionPresenterCrossRef
import com.mad.e.twccomposeplanner.core.database.model.*
import com.mad.e.twccomposeplanner.core.database.util.InstantConverter
import com.mad.e.twccomposeplanner.core.database.util.NewsResourceTypeConverter


@Database(
    entities = [
        PresenterEntity::class,
        SessionPresenterCrossRef::class,
        SessionEntity::class,
        TopicEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
    NewsResourceTypeConverter::class,
)
abstract class TwcDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
    abstract fun presenterDao(): PresenterDao
    abstract fun sessionDao(): SessionDao
}
