package com.caffmaniac.whosthat.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 1,
    @ColumnInfo(name = "phoneNumber") val phoneNumber: String?,
    @ColumnInfo(name = "countryCode") val countryCode: String?,
    @ColumnInfo(name = "aliasName") val aliasName: String?,
    @ColumnInfo(name = "url") val intentUrl: String?
)