package com.caffmaniac.whosthat.app.domain.model

data class UserHistoryDataItem(
    val name: String? = null,
    val phoneNumber: String,
    val isFirstItem: Boolean = false,
    val isLastItem: Boolean = false
)