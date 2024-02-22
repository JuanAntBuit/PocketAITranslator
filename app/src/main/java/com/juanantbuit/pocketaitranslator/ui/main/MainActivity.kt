/**  PocketAITranslator Android application to translate using an AI API
 *  Copyright (C) 2023 Juan Antonio Buitrago Balsalobre
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
package com.juanantbuit.pocketaitranslator.ui.main


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juanantbuit.pocketaitranslator.R
import com.juanantbuit.pocketaitranslator.ui.components.LanguageDropdownMenu
import com.juanantbuit.pocketaitranslator.ui.components.LanguageSelectionButtons
import com.juanantbuit.pocketaitranslator.ui.components.MyBottomNavigation
import com.juanantbuit.pocketaitranslator.ui.components.MyHeader
import com.juanantbuit.pocketaitranslator.ui.theme.PocketAITranslatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val availableLanguages =
            resources.getStringArray(R.array.available_languages).toList().sorted()
        viewModel.setAvailableLanguages(availableLanguages)

        setContent {
            PocketAITranslatorTheme {
                Scaffold(
                    topBar = { MyHeader() },
                    bottomBar = { MyBottomNavigation() }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            LanguageSelectionButtons()

                            LanguageDropdownMenu(
                                expanded = viewModel.expanded.value,
                                setExpanded = { viewModel.setExpanded(it) },
                                onDismissRequest = { viewModel.expanded.value = false },
                                searchQuery = viewModel.searchQuery.value,
                                onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
                                filteredLanguages = viewModel.searchResults.value,
                                onLanguageSelected = { viewModel.onLanguageSelected(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun Previews() {
    PocketAITranslatorTheme(darkTheme = true) {
        Surface {
        }
    }
}