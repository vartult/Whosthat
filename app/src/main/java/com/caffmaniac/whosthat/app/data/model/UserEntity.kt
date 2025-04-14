package com.caffmaniac.whosthat.app.data.model

import androidx.compose.runtime.Composable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = "phoneNumber") val phoneNumber: String,
    @ColumnInfo(name = "aliasName") val aliasName: String?,
    @ColumnInfo(name = "timeStamp") val timeStamp: Long,
)