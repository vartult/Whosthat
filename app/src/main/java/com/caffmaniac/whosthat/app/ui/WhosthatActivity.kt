package com.caffmaniac.whosthat.app.ui

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.caffmaniac.whosthat.app.domain.WhosthatScreenEvent
import com.caffmaniac.whosthat.app.domain.model.WhosthatScreenState
import com.caffmaniac.whosthat.app.domain.WhosthatViewModel
import com.caffmaniac.whosthat.app.ui.phonenumber.SendMessageFormUI
import com.caffmaniac.whosthat.app.ui.search.SearchHistory
import com.caffmaniac.whosthat.app.ui.toolbar.Toolbar
import com.caffmaniac.whosthat.composeui.theme.WhosthatDynamicTheme
import dagger.hilt.android.AndroidEntryPoint
import java.net.URISyntaxException

@AndroidEntryPoint
class WhosthatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhosthatDynamicTheme {
                UI()
            }
        }
    }

    @Composable
    private fun UI() {
        val viewModel: WhosthatViewModel = viewModel()
        BuildUI(state = viewModel.uiState(), onEventHandler = viewModel::onEvent)
    }

    @Composable
    private fun BuildUI(
        state: WhosthatScreenState,
        onEventHandler: (WhosthatScreenEvent) -> Unit = {}
    ) {
        LaunchedEffect(key1 = state.whatsappData.value) {
            state.whatsappData.value?.let { safeWhatsappData ->
                openInWhatsapp(safeWhatsappData.phoneNo, safeWhatsappData.message)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            Toolbar(Modifier)
            SendMessageFormUI(Modifier, state, onEventHandler)
            SearchHistory(state, onEventHandler)
        }
    }

    private fun openInWhatsapp(phoneNo: String, message: String) {
        try {
            val intent = Intent("android.intent.action.MAIN")
            intent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
            intent.putExtra("Whatsapp Redirect Link", "$phoneNo@s.whatsapp.net")
            intent.putExtra("Display Name", "+$phoneNo")
            startActivity(
                Intent.parseUri(
                    "https://api.whatsapp.com/send?phone=$phoneNo&text=$message",
                    0
                )
            )
        } catch (ignore: URISyntaxException) {
            ignore.printStackTrace()
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(applicationContext, "Whatsapp not installed", Toast.LENGTH_LONG).show()
        }
    }
}