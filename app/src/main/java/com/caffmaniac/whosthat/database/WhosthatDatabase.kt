package com.caffmaniac.whosthat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.caffmaniac.whosthat.app.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class WhosthatDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}