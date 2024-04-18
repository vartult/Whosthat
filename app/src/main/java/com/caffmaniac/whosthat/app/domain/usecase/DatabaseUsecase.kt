package com.caffmaniac.whosthat.app.domain.usecase

import com.caffmaniac.whosthat.app.data.WhosthatRepository
import com.caffmaniac.whosthat.app.data.model.UserEntity
import javax.inject.Inject

class DatabaseUsecase @Inject constructor(
    private val repository: WhosthatRepository
) {
    suspend fun getAllUserList(): List<UserEntity> {
        return repository.getAllUserList()
    }

    suspend fun saveUserData(phoneNumber: String, alias: String, url: String, countryCode: String) {
        repository.saveUserData(
            UserEntity(
                phoneNumber = phoneNumber,
                aliasName = alias,
                intentUrl = url,
                countryCode = countryCode
            )
        )
    }
}