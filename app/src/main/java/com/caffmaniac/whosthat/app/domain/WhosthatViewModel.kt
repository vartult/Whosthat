package com.caffmaniac.whosthat.app.domain

import android.telephony.PhoneNumberUtils
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.caffmaniac.whosthat.app.domain.model.UserHistoryDataItem
import com.caffmaniac.whosthat.app.domain.usecase.DatabaseUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WhosthatViewModel @Inject constructor(private val databaseUsecase: DatabaseUsecase) :
    ViewModel() {

    private val userDataList = mutableStateOf<List<UserHistoryDataItem>>(emptyList())
    private val phoneNumber = mutableStateOf("")
    private val alias = mutableStateOf("")
    private val message = mutableStateOf("")
    private val phoneNumberError = mutableStateOf(false)
    private val isProcessingMsgRequest = mutableStateOf(false)

    @Composable
    fun uiState(): WhosthatScreenState {
        LaunchedEffect(Unit) {
            start()
        }

        return WhosthatScreenState(
            userList = getUserHistoryList(),
            phoneNumber = getPhoneNumber(),
            alias = getAlias(),
            message = getMessage(),
            isError = getError(),
            isProcessingMsgRequest = getIsProcessingMsgRequest()
        )
    }

    private fun getIsProcessingMsgRequest() = isProcessingMsgRequest

    private fun getError() = phoneNumberError

    private fun getMessage() = message

    private fun getAlias() = alias

    private fun getPhoneNumber() = phoneNumber

    private fun getUserHistoryList() = userDataList.value

    private fun start() {
        // Fetch all txn list

    }

    fun onEvent(event: WhosthatScreenEvent) {
        when (event) {
            is WhosthatScreenEvent.OnItemClicked -> {
                //Trigger whatsapp
            }

            is WhosthatScreenEvent.OnSendMessage -> {
                // Validate Phone Number
                if (PhoneNumberUtils.isGlobalPhoneNumber(event.phoneNumber)) {
                    //Trigger whatsapp
                    isProcessingMsgRequest.value = true
                    // Save data
                    //databaseUsecase.getAllUserList()
                    Log.d("ViewModel", event.message)
                    // Clear Data
                    phoneNumber.value = ""
                    alias.value = ""
                    message.value = ""
                } else {
                    phoneNumberError.value = true
                }
            }
        }
    }

}