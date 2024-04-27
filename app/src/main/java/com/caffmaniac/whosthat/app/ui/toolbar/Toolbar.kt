package com.caffmaniac.whosthat.app.ui.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.caffmaniac.whosthat.R
import com.caffmaniac.whosthat.composeui.theme.WhosthatDynamicTheme

@Composable
fun Toolbar(modifier: Modifier) {
    Box(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = "Whosthat",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
//        Icon(
//            painter = painterResource(id = R.drawable.ic_settings),
//            contentDescription = "Settings",
//            modifier = modifier
//                .padding(top = 8.dp, start = 8.dp, bottom = 8.dp)
//                .size(28.dp)
//                .align(Alignment.CenterEnd),
//            tint = MaterialTheme.colorScheme.secondary
//        )
    }
}


@Preview(showBackground = true)
@Composable
fun ToolbarPreviewLight() {
    WhosthatDynamicTheme() {
        Toolbar(Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun ToolbarPreviewDark() {
    WhosthatDynamicTheme(darkTheme = true) {
        Toolbar(Modifier)
    }
}