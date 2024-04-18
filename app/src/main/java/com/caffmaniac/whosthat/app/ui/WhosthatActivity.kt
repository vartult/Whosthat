package com.caffmaniac.whosthat.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.caffmaniac.whosthat.app.domain.WhosthatScreenEvent
import com.caffmaniac.whosthat.app.domain.WhosthatScreenState
import com.caffmaniac.whosthat.app.domain.WhosthatViewModel
import com.caffmaniac.whosthat.app.ui.phonenumber.SendMessage
import com.caffmaniac.whosthat.app.ui.search.Search
import com.caffmaniac.whosthat.app.ui.toolbar.Toolbar
import com.caffmaniac.whosthat.composeui.theme.WhosthatDynamicTheme
import dagger.hilt.android.AndroidEntryPoint

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
        val state = viewModel
        BuildUI(state = viewModel.uiState(), onEventHandler = viewModel::onEvent)
    }

    @Composable
    private fun BuildUI(
        state: WhosthatScreenState,
        onEventHandler: (WhosthatScreenEvent) -> Unit = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Toolbar(Modifier)
            SendMessage(Modifier, state, onEventHandler)
            Search(state, onEventHandler)
        }
    }
}