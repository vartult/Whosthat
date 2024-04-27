package com.caffmaniac.whosthat.app.domain

import android.R.attr.label
import android.R.attr.text
import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.telephony.PhoneNumberUtils
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caffmaniac.whosthat.app.data.model.UserEntity
import com.caffmaniac.whosthat.app.domain.model.UserHistoryDataItem
import com.caffmaniac.whosthat.app.domain.model.WhatsappData
import com.caffmaniac.whosthat.app.domain.model.WhosthatScreenState
import com.caffmaniac.whosthat.app.domain.usecase.DatabaseUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WhosthatViewModel @Inject constructor(
    private val databaseUsecase: DatabaseUsecase,
    private val application: Application
) :
    AndroidViewModel(application) {

    private val userDataList = mutableStateOf<List<UserHistoryDataItem>>(emptyList())
    private val phoneNumber = mutableStateOf("")
    private val alias = mutableStateOf("")
    private val message = mutableStateOf("")
    private val phoneNumberError = mutableStateOf(false)
    private val isProcessingMsgRequest = mutableStateOf(false)
    private val whatsappData = mutableStateOf<WhatsappData?>(null)

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
            isProcessingMsgRequest = getIsProcessingMsgRequest(),
            whatsappData = getWhatsappData()
        )
    }

    private fun getWhatsappData() = whatsappData

    private fun getIsProcessingMsgRequest() = isProcessingMsgRequest

    private fun getError() = phoneNumberError

    private fun getMessage() = message

    private fun getAlias() = alias

    private fun getPhoneNumber() = phoneNumber

    private fun getUserHistoryList() = userDataList

    private fun start() {
        // Fetch all txn list
        viewModelScope.launch(Dispatchers.IO) {
            val userEntityList = databaseUsecase.getAllUserList()
            userDataList.value = mapToUserHistoryItemDataList(userEntityList)
        }
    }

    private fun mapToUserHistoryItemDataList(userEntityList: List<UserEntity>): List<UserHistoryDataItem> {
        return mutableListOf<UserHistoryDataItem>().apply {
            userEntityList.forEachIndexed { index, userEntity ->
                userEntity.aliasName?.let { aliasName ->
                    when (index) {
                        0 -> {
                            add(
                                UserHistoryDataItem(
                                    aliasName,
                                    phoneNumber = userEntity.phoneNumber,
                                    isFirstItem = true
                                )
                            )
                        }

                        userEntityList.size - 1 -> {
                            add(
                                UserHistoryDataItem(
                                    aliasName,
                                    phoneNumber = userEntity.phoneNumber,
                                    isLastItem = true
                                )
                            )
                        }

                        else -> {
                            add(
                                UserHistoryDataItem(
                                    aliasName,
                                    phoneNumber = userEntity.phoneNumber
                                )
                            )
                        }
                    }
                } ?: run {
                    when (index) {
                        0 -> {
                            add(
                                UserHistoryDataItem(
                                    phoneNumber = userEntity.phoneNumber,
                                    isFirstItem = true
                                )
                            )
                        }

                        userEntityList.size - 1 -> {
                            add(
                                UserHistoryDataItem(
                                    phoneNumber = userEntity.phoneNumber,
                                    isLastItem = true
                                )
                            )
                        }

                        else -> {
                            add(UserHistoryDataItem(phoneNumber = userEntity.phoneNumber))
                        }
                    }
                }
            }
        }

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
                    whatsappData.value = WhatsappData(event.phoneNumber, event.message)
                    isProcessingMsgRequest.value = true
                    // Save data
                    viewModelScope.launch(Dispatchers.IO) {
                        databaseUsecase.saveUserData(event.phoneNumber, event.alias)
                        val userEntityList = databaseUsecase.getAllUserList()
                        userDataList.value = mapToUserHistoryItemDataList(userEntityList)
                    }
                    Log.d("ViewModel", event.message)
                    // Clear Data
                    phoneNumber.value = ""
                    alias.value = ""
                    message.value = ""
                } else {
                    whatsappData.value = null
                    phoneNumberError.value = true
                }
            }

            is WhosthatScreenEvent.OnSwipeToCopyNumber -> {

            }

            is WhosthatScreenEvent.OnSwipeToTriggerWhatsapp -> {
                whatsappData.value = WhatsappData(event.phoneNumber, "")
            }
        }
    }

}