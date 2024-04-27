package com.caffmaniac.whosthat.app.domain.model

import androidx.compose.runtime.MutableState

data class WhosthatScreenState(
    val userList: MutableState<List<UserHistoryDataItem>>,
    val phoneNumber: MutableState<String>,
    val alias: MutableState<String>,
    val message: MutableState<String>,
    val isError: MutableState<Boolean>,
    val isProcessingMsgRequest: MutableState<Boolean>,
    val whatsappData: MutableState<WhatsappData?>
)