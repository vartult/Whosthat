package com.caffmaniac.whosthat.app.domain.model

data class UserHistoryDataItem(
    val name: String,
    val isTerminalItem: Boolean = false
)