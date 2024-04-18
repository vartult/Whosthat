package com.caffmaniac.whosthat.app.domain

sealed interface WhosthatScreenEvent {
    data class OnSendMessage(
        val phoneNumber: String,
        val alias: String,
        val message: String
    ) : WhosthatScreenEvent

    data class OnItemClicked(val intentUrl: String) : WhosthatScreenEvent
}