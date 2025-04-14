package com.caffmaniac.whosthat.app.domain

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.caffmaniac.whosthat.app.data.model.UserEntity
import com.caffmaniac.whosthat.app.domain.model.UserHistoryDataItem
import com.caffmaniac.whosthat.app.domain.model.WhatsappData
import com.caffmaniac.whosthat.app.domain.model.WhosthatScreenState
import com.caffmaniac.whosthat.app.domain.model.WhosthatUiState
import com.caffmaniac.whosthat.app.domain.usecase.DatabaseUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WhosthatViewModel @Inject constructor(
    private val databaseUsecase: DatabaseUsecase,
    private val application: Application
) :
    AndroidViewModel(application) {

    private val userDataList = mutableStateListOf<UserHistoryDataItem>()
    private val phoneNumber = mutableStateOf("")
    private val alias = mutableStateOf("")
    private val message = mutableStateOf("")
    private val phoneNumberError = mutableStateOf<String?>(null)
    private val isProcessingMsgRequest = mutableStateOf(false)
    private val whatsappData = mutableStateOf<WhatsappData?>(null)
    private val whosthatUiState = mutableStateOf<WhosthatUiState<Unit>>(WhosthatUiState.Idle)

    @Composable
    fun uiState(): WhosthatScreenState {
        LaunchedEffect(Unit) {
            start()
        }

        return WhosthatScreenState(
            userList = getUserHistoryList(),
            userSearchUiState = getUiState(),
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

    private fun getUiState() = whosthatUiState

    private fun start() {
        // Fetch all txn list
        viewModelScope.launch(Dispatchers.IO) {
            val userEntityList = databaseUsecase.getAllSearchHistory()
            userDataList.addAll(mapToUserHistoryItemDataList(userEntityList))
            whosthatUiState.value = WhosthatUiState.Success(Unit)
        }
    }

    private fun mapToUserHistoryItemDataList(userEntityList: List<UserEntity>): List<UserHistoryDataItem> {
        return mutableListOf<UserHistoryDataItem>().apply {
            userEntityList.sortedByDescending { it.timeStamp }.forEach { userEntity ->
                userEntity.aliasName?.let { aliasName ->
                    add(
                        UserHistoryDataItem(name = aliasName, phoneNumber = userEntity.phoneNumber)
                    )
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
                if (isValidPhoneNumber(event.phoneNumber)) {
                    //Trigger whatsapp
                    whatsappData.value = WhatsappData(event.phoneNumber, event.message)
                    isProcessingMsgRequest.value = true
                    // Save data
                    viewModelScope.launch(Dispatchers.IO) {
                        databaseUsecase.saveUserData(
                            event.phoneNumber,
                            event.alias,
                            System.currentTimeMillis()
                        )
                        val userEntity = databaseUsecase.getHistoryByNumber(event.phoneNumber)
                        userDataList.add(0, mapToUserHistoryItemDataList(userEntity).first())
                    }
                    // Clear Data
                    clearFields()
                }
            }

            is WhosthatScreenEvent.OnSwipeToCopyNumber -> {
                event.context.handleCopyText(event.phoneNumber)
            }

            is WhosthatScreenEvent.OnSwipeToTriggerWhatsapp -> {
                whatsappData.value = WhatsappData(event.phoneNumber, "")
            }
        }
    }

    private fun Context.handleCopyText(phoneNumber: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("Whosthat phone no", phoneNumber)
        clipboard.setPrimaryClip(clip)
    }

    private fun clearFields() {
        phoneNumber.value = ""
        alias.value = ""
        message.value = ""
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // Reset error text
        phoneNumberError.value = ""

        // Remove any whitespace, dashes, or parentheses
        val cleanedNumber = phoneNumber.replace(Regex("[\\s\\-()]"), "")

        // Check if the number is empty
        if (cleanedNumber.isEmpty()) {
            phoneNumberError.value = "Phone number cannot be empty"
            return false
        }

        // Check if the number contains only digits
        if (!cleanedNumber.matches(Regex("^\\d+$"))) {
            phoneNumberError.value =
                "Phone number can only contain digits, spaces, dashes, and parentheses"
            return false
        }

        // Check for minimum and maximum length
        if (cleanedNumber.length < 10 || cleanedNumber.length > 15) {
            phoneNumberError.value = "Phone number must be between 10-15 digits"
            return false
        }

        // Check if it starts with a plus sign (optional)
        if (phoneNumber.startsWith("+") && !phoneNumber.matches(Regex("^\\+[0-9]+$"))) {
            phoneNumberError.value = "Invalid format for international phone number"
            return false
        }

        return true
    }

}