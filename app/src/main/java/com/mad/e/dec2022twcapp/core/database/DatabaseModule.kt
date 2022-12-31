package com.mad.e.dec2022twcapp.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesTwcDatabase(
        @ApplicationContext context: Context,
    ): TwcDatabase = Room.databaseBuilder(
        context,
        TwcDatabase::class.java,
        "twc-database"
    ).build()
}


