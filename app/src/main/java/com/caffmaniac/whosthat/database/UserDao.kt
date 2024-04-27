package com.caffmaniac.whosthat.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.caffmaniac.whosthat.app.data.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    suspend fun getAll(): List<UserEntity>

    @Query("DELETE FROM UserEntity")
    suspend fun deleteAllUser()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)
}