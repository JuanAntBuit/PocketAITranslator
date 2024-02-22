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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juanantbuit.pocketaitranslator.R
import com.juanantbuit.pocketaitranslator.ui.main.MainViewModel
import com.juanantbuit.pocketaitranslator.ui.theme.InterFamily

data class LanguageSelectorParams(
    val cornerShape: RoundedCornerShape,
    val alignment: Alignment,
    val fillMaxWidthFraction: Float,
    val selectedLanguage: String
)

@Composable
fun LanguageSelectionButtons() {
    Row {
        FromLanguageSelectorButton()
        ToLanguageSelectorButton()
    }
}

@Composable
fun FromLanguageSelectorButton() {
    val viewModel: MainViewModel = viewModel()
    val selectedLanguage = viewModel.fromLanguage.value ?: stringResource(R.string.select_language)
    LanguageSelector(
        params = LanguageSelectorParams(
            cornerShape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
            alignment = Alignment.BottomStart,
            fillMaxWidthFraction = 0.5f,
            selectedLanguage = selectedLanguage
        ),
        isFromLanguage = true
    )
}

@Composable
fun ToLanguageSelectorButton() {
    val viewModel: MainViewModel = viewModel()
    val selectedLanguage = viewModel.toLanguage.value ?: stringResource(R.string.select_language)
    LanguageSelector(
        params = LanguageSelectorParams(
            cornerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
            alignment = Alignment.BottomEnd,
            fillMaxWidthFraction = 1f,
            selectedLanguage = selectedLanguage
        ),
        isFromLanguage = false
    )
}

@Composable
fun LanguageSelector(params: LanguageSelectorParams, isFromLanguage: Boolean) {
    val viewModel: MainViewModel = viewModel()

    Box(contentAlignment = Alignment.Center) {
        Button(
            modifier = Modifier
                .fillMaxWidth(params.fillMaxWidthFraction),
            onClick = {
                viewModel.setExpanded(true)
                if (isFromLanguage) {
                    viewModel.onFromLanguageSelected(params.selectedLanguage)
                } else {
                    viewModel.onToLanguageSelected(params.selectedLanguage)
                }
            },
            shape = params.cornerShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
        ) {
            Text(
                text = params.selectedLanguage,
                fontSize = 16.sp,
                fontFamily = InterFamily,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }
    }
}


@Composable
fun LanguageDropdownMenu(
    expanded: Boolean,
    setExpanded: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    filteredLanguages: List<String>,
    onLanguageSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 32.dp, end = 32.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                setExpanded(false)
                onDismissRequest()
            }
        ) {
            TextField(
                value = searchQuery,
                onValueChange = {
                    onSearchQueryChange(it)
                },
                label = { Text(stringResource(R.string.search)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            if (filteredLanguages.isEmpty()) {
                DropdownMenuItem(
                    onClick = {},
                    text = { Text(stringResource(R.string.no_match_language)) }
                )
            } else {
                for (language in filteredLanguages) {
                    DropdownMenuItem(
                        onClick = {
                            setExpanded(false)
                            onLanguageSelected(language)
                        },
                        text = { Text(language) }
                    )

                }
            }
        }
    }
}
