package com.caffmaniac.whosthat.app.ui.phonenumber

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.caffmaniac.whosthat.R
import com.caffmaniac.whosthat.app.domain.WhosthatScreenEvent
import com.caffmaniac.whosthat.app.domain.model.WhosthatScreenState

@Composable
fun SendMessage(
    modifier: Modifier,
    state: WhosthatScreenState,
    onEventHandler: (WhosthatScreenEvent) -> Unit
) {
    var phoneNumber: String by rememberSaveable {
        state.phoneNumber
    }
    var alias: String by rememberSaveable {
        state.alias
    }
    var message: String by rememberSaveable {
        state.message
    }
    var isError: Boolean by rememberSaveable {
        state.isError
    }

    var isProcessingMsgRequest: Boolean by rememberSaveable {
        state.isProcessingMsgRequest
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            PhoneNumberInputField(modifier = Modifier, phoneNumber, isError, {
                isError = false
            }) {
                phoneNumber = it
            }
            Spacer(modifier = Modifier.height(4.dp))
            TextInputField(
                modifier = Modifier,
                label = "Alias",
                painter = painterResource(id = R.drawable.ic_contact),
                imgDesc = "Alias",
                imeAction = ImeAction.Next,
                text = alias,
                onEventHandler = onEventHandler,
                phoneNumber = phoneNumber,
                alias = alias,
                message = message
            ) {
                alias = it
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextInputField(
                modifier = Modifier,
                label = "Message",
                painter = painterResource(id = R.drawable.ic_chat_bubble),
                imgDesc = "Message",
                imeAction = ImeAction.Done,
                text = message,
                onEventHandler = onEventHandler,
                phoneNumber = phoneNumber,
                alias = alias,
                message = message
            ) {
                message = it
            }
            Spacer(modifier = Modifier.height(16.dp))
            PhoneButton(onEventHandler, phoneNumber, alias, message, isProcessingMsgRequest)
        }
    }
}

@Composable
fun PhoneButton(
    onEventHandler: (WhosthatScreenEvent) -> Unit,
    phoneNumber: String,
    alias: String,
    message: String,
    isProcessingMsgRequest: Boolean
) {
    Button(modifier = Modifier.fillMaxWidth(), shape = ButtonDefaults.elevatedShape, onClick = {
        onEventHandler.invoke(
            WhosthatScreenEvent.OnSendMessage(
                phoneNumber = phoneNumber,
                alias = alias,
                message = message
            )
        )
    }) {
//        if (isProcessingMsgRequest) {
//            CircularProgressIndicator(
//                modifier = Modifier
//                    .wrapContentHeight()
//            )
//        } else
//            Text(text = "Send Message")
        Text(text = "Send Message")
    }
}

@Composable
fun PhoneNumberInputField(
    modifier: Modifier,
    phoneNumber: String,
    isError: Boolean,
    errorStateCallback: (Boolean) -> Unit,
    liveText: (String) -> Unit
) {
    val maxCharLimit = 10
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = phoneNumber,
        onValueChange = { newText ->
            if (maxCharLimit >= newText.length)
                liveText.invoke(newText)
            if (isError) {
                errorStateCallback.invoke(false)
            }
        },
        label = { Text(text = "Phone Number") },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_call),
                contentDescription = "Phone"
            )
        },
        prefix = {
            Text(text = "+91")
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Phone
        ),
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Invalid Phone Number",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextInputField(
    modifier: Modifier,
    label: String,
    painter: Painter,
    imgDesc: String,
    imeAction: ImeAction,
    text: String,
    onEventHandler: (WhosthatScreenEvent) -> Unit,
    phoneNumber: String,
    alias: String,
    message: String,
    liveText: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val kc = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = { newText ->
            liveText.invoke(newText)
        },
        label = { Text(text = label) },
        leadingIcon = {
            Icon(
                painter = painter,
                contentDescription = imgDesc
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = {
                onEventHandler.invoke(
                    WhosthatScreenEvent.OnSendMessage(
                        phoneNumber = phoneNumber,
                        alias = alias,
                        message = message
                    )
                )
                focusManager.clearFocus()
                kc?.hide()
            },
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        )
    )
}
