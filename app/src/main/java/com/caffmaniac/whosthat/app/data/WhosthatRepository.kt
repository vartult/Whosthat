package com.caffmaniac.whosthat.app.data

import com.caffmaniac.whosthat.app.data.model.UserEntity
import com.caffmaniac.whosthat.database.UserDao
import com.caffmaniac.whosthat.database.WhosthatDatabase
import javax.inject.Inject

class WhosthatRepository @Inject constructor(private val userDao: UserDao) {
    suspend fun getAllUserList(): List<UserEntity> {
        return userDao.getAll()
    }

    suspend fun saveUserData(userEntity: UserEntity) {
        return userDao.insertAll(userEntity)
    }
}