package com.caffmaniac.whosthat.app.domain

import android.content.Context

sealed interface WhosthatScreenEvent {
    data class OnSendMessage(
        val phoneNumber: String,
        val alias: String,
        val message: String
    ) : WhosthatScreenEvent

    data class OnItemClicked(val intentUrl: String) : WhosthatScreenEvent
    data class OnSwipeToTriggerWhatsapp(val phoneNumber: String) : WhosthatScreenEvent
    data class OnSwipeToCopyNumber(val context: Context, val phoneNumber: String) :
        WhosthatScreenEvent
}