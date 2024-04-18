package com.caffmaniac.whosthat.app.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.caffmaniac.whosthat.app.domain.WhosthatScreenEvent
import com.caffmaniac.whosthat.app.domain.WhosthatScreenState

val stroke = Stroke(width = 2f,
    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
)
@Composable
fun Search(state: WhosthatScreenState, onEventHandler: (WhosthatScreenEvent) -> Unit) {
    ItemListEmptyView()
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
            color = colorScheme.secondary,
            textAlign = TextAlign.Center
        )
    }
}