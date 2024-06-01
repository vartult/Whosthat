package com.caffmaniac.whosthat.app.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.caffmaniac.whosthat.R
import com.caffmaniac.whosthat.app.domain.model.UserHistoryDataItem
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun PreviousContactSection(
    userHistoryDataItem: UserHistoryDataItem,
    onSwipeTriggerWhatsApp: (String) -> Unit,
    onSwipeTriggerCopy: (String) -> Unit
) {
    val triggerWhatsApp = SwipeAction(
        onSwipe = {
            onSwipeTriggerWhatsApp(userHistoryDataItem.phoneNumber)
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_whatsapp),
                contentDescription = "Open in whatsapp",
                modifier = Modifier.padding(16.dp),
                tint = Color.Unspecified
            )
        },
        background = Color(0xFF858282).copy(alpha = 0.7f),
        isUndo = true
    )
    val triggerCopy = SwipeAction(
        onSwipe = {
            onSwipeTriggerCopy(userHistoryDataItem.phoneNumber)
        },
        icon = {
            Icon(
                painterResource(id = R.drawable.ic_copy),
                contentDescription = "Copy Phone Number",
                modifier = Modifier.padding(16.dp),
                tint = Color.Unspecified
            )
        },
        background = Color(0xFF858282).copy(alpha = 0.7f),
        isUndo = true
    )
    SwipeableActionsBox(
        modifier = Modifier,
        swipeThreshold = 200.dp,
        startActions = listOf(triggerWhatsApp),
        endActions = listOf(triggerCopy)
    ) {
        PreviousContactItem(userHistoryDataItem)
    }
}

@Composable
fun PreviousContactItem(userHistoryDataItem: UserHistoryDataItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = getItemShape(userHistoryDataItem.isFirstItem, userHistoryDataItem.isLastItem)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_avatar),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer

                )
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    text = if (userHistoryDataItem.name.isNullOrEmpty()) userHistoryDataItem.phoneNumber
                    else userHistoryDataItem.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Unspecified
                )
            }
            if (!userHistoryDataItem.isLastItem)
                Divider(color = MaterialTheme.colorScheme.onSecondaryContainer, thickness = 1.dp)
        }
    }
}

@Composable
private fun getItemShape(firstItem: Boolean, lastItem: Boolean): RoundedCornerShape {
    return if (firstItem) {
        RoundedCornerShape(8.dp, 8.dp, 0.dp, 0.dp)
    } else if (lastItem) {
        RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp)
    } else {
        RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp)
    }
}