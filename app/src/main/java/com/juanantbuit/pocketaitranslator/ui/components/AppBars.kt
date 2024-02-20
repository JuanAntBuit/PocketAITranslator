/**  PocketAITranslator Android application to translate using an AI API
 *  Copyright (C) 2024 Juan Antonio Buitrago Balsalobre
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.juanantbuit.pocketaitranslator.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juanantbuit.pocketaitranslator.R
import com.juanantbuit.pocketaitranslator.ui.theme.InterFamily

@Composable
fun MyHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(0.45f))
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier.weight(1f),
            fontFamily = InterFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_settings),
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun MyBottomNavigation() {
    val items = listOf("History", "Translate", "Statistics")
    var selectedItem by remember { mutableStateOf(items[1]) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondary
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = getIconForItem(item),
                        contentDescription = item
                    )
                },
                label = {
                    Text(
                        text = stringResource(getResourceIdForLabel(item))
                    )
                },
                selected = selectedItem == item,
                onClick = { selectedItem = item },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onTertiary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onTertiary
                )
            )
        }
    }
}

@Composable
fun getIconForItem(item: String): Painter {
    return when (item) {
        "History" -> painterResource(R.drawable.ic_history)
        "Translate" -> painterResource(R.drawable.ic_translate)
        "Statistics" -> painterResource(R.drawable.ic_stats)
        else -> painterResource(R.drawable.ic_translate) // default icon
    }
}

fun getResourceIdForLabel(label: String): Int {
    return when (label) {
        "History" -> R.string.history_btn
        "Translate" -> R.string.translate_btn
        "Statistics" -> R.string.stats_btn
        else -> R.string.translate_btn // default label
    }
}