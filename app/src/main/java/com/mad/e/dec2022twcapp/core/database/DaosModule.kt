package com.mad.e.twccomposeplanner.core.database

import com.mad.e.dec2022twcapp.core.database.TwcDatabase
import com.mad.e.dec2022twcapp.core.database.dao.PresenterDao
import com.mad.e.dec2022twcapp.core.database.dao.SessionDao
import com.mad.e.dec2022twcapp.core.database.dao.TopicDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesPresenterDao(
        database: TwcDatabase,
    ): PresenterDao = database.presenterDao()

    @Provides
    fun providesTopicsDao(
        database: TwcDatabase,
    ): TopicDao = database.topicDao()

    @Provides
    fun providesSessionDao(
        database: TwcDatabase,
    ): SessionDao = database.sessionDao()

}
