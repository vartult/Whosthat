package com.caffmaniac.whosthat.app.domain

import androidx.compose.runtime.MutableState
import com.caffmaniac.whosthat.app.domain.model.UserHistoryDataItem
import kotlinx.coroutines.flow.StateFlow

data class WhosthatScreenState(
    val userList: List<UserHistoryDataItem>,
    val phoneNumber: MutableState<String>,
    val alias: MutableState<String>,
    val message: MutableState<String>,
    val isError: MutableState<Boolean>,
    val isProcessingMsgRequest: MutableState<Boolean>
)