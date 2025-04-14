package com.caffmaniac.whosthat.app.domain.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList

data class WhosthatScreenState(
    val userList: SnapshotStateList<UserHistoryDataItem>,
    val phoneNumber: MutableState<String>,
    val alias: MutableState<String>,
    val message: MutableState<String>,
    val isError: MutableState<String?>,
    val isProcessingMsgRequest: MutableState<Boolean>,
    val whatsappData: MutableState<WhatsappData?>
)