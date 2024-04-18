package com.caffmaniac.whosthat.injection

import android.content.Context
import androidx.room.Room
import com.caffmaniac.whosthat.database.UserDao
import com.caffmaniac.whosthat.database.WhosthatDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class WhosthatModule {

    @Provides
    @Singleton
    fun bindDatabase(
        whosthatDatabase: WhosthatDatabase
    ): UserDao {
        return whosthatDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): WhosthatDatabase {
        return Room.databaseBuilder(
            appContext,
            WhosthatDatabase::class.java,
            "WhosthatDatabase"
        ).build()
    }
}