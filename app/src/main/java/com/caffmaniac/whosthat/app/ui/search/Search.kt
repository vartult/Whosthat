package com.caffmaniac.whosthat.app.ui.search

import android.graphics.Paint.Style
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.caffmaniac.whosthat.app.domain.WhosthatScreenEvent
import com.caffmaniac.whosthat.app.domain.model.WhosthatScreenState

val stroke = Stroke(
    width = 2f,
    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
)

@Composable
fun Search(state: WhosthatScreenState, onEventHandler: (WhosthatScreenEvent) -> Unit, onSwipeTriggerCopy: (String) -> Unit) {
    val userList by rememberSaveable {
        state.userList
    }
    if (userList.isEmpty())
        ItemListEmptyView()
    else {
        Column(
            modifier = Modifier.padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        ) {
            Text(
                text = "Recents",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Card(
                modifier = Modifier.padding(top = 16.dp),
                shape = ShapeDefaults.Small
            ) {
                LazyColumn {
                    item {
                        userList.forEach { userHistoryDataItem ->
                            PreviousContactSection(
                                userHistoryDataItem = userHistoryDataItem,
                                onSwipeTriggerCopy = {
                                    onSwipeTriggerCopy.invoke(it)
                                },
                                onSwipeTriggerWhatsApp = {
                                    onEventHandler.invoke(
                                        WhosthatScreenEvent.OnSwipeToTriggerWhatsapp(
                                            it
                                        )
                                    )
                                })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemListEmptyView() {
    val colorScheme = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxSize()
            .drawBehind {
                drawRoundRect(
                    color = colorScheme.secondary,
                    style = stroke,
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Send your first message without saving contact!",
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }
}