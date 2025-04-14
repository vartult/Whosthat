package com.caffmaniac.whosthat.app.domain.usecase

import com.caffmaniac.whosthat.app.data.WhosthatRepository
import com.caffmaniac.whosthat.app.data.model.UserEntity
import javax.inject.Inject

class DatabaseUsecase @Inject constructor(
    private val repository: WhosthatRepository
) {
    suspend fun getAllSearchHistory(): List<UserEntity> {
        return repository.getAllUserList()
    }

    suspend fun getHistoryByNumber(phoneNumber: String): List<UserEntity> {
        return repository.getUserByNumber(phoneNumber)
    }

    suspend fun saveUserData(phoneNumber: String, alias: String, currentTimeStamp: Long) {
        repository.saveUserData(
            UserEntity(
                phoneNumber = phoneNumber,
                aliasName = alias,
                timeStamp = currentTimeStamp
            )
        )
    }
}