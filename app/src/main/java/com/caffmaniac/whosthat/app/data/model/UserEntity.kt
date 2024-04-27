package com.caffmaniac.whosthat.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = "phoneNumber") val phoneNumber: String,
    @ColumnInfo(name = "aliasName") val aliasName: String?,
)