package com.juanantbuit.pocketaitranslator.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private var availableLanguages: List<String> = listOf()
    private var lastIsFromLanguageSelected = false

    val fromLanguage = mutableStateOf<String?>(null)
    val toLanguage = mutableStateOf<String?>(null)
    val expanded = mutableStateOf(false)
    val searchQuery = mutableStateOf("")
    private var filteredLanguages = mutableStateOf(listOf<String>())
    var searchResults = mutableStateOf(listOf<String>())

    fun onFromLanguageSelected(selectedLanguage: String) {
        handleLanguageSelection(selectedLanguage, true)
    }

    fun onToLanguageSelected(selectedLanguage: String) {
        handleLanguageSelection(selectedLanguage, false)
    }

    private fun handleLanguageSelection(selectedLanguage: String, isFromLanguage: Boolean) {
        val currentLanguage = if (isFromLanguage) fromLanguage.value else toLanguage.value
        currentLanguage?.let {
            if (availableLanguages.contains(it)) {
                filteredLanguages.value = filteredLanguages.value + it
            }
        }

        if (isFromLanguage) {
            fromLanguage.value = selectedLanguage
        } else {
            toLanguage.value = selectedLanguage
        }

        filteredLanguages.value = filteredLanguages.value - selectedLanguage
        searchResults.value = filteredLanguages.value
        searchQuery.value = ""
        lastIsFromLanguageSelected = isFromLanguage
    }

    fun onLanguageSelected(selectedLanguage: String) {
        if (lastIsFromLanguageSelected) {
            onFromLanguageSelected(selectedLanguage)
        } else {
            onToLanguageSelected(selectedLanguage)
        }
    }

    fun setExpanded(value: Boolean) {
        expanded.value = value
    }

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
        searchResults.value =
            filteredLanguages.value.filter { it.contains(query, ignoreCase = true) }
    }

    fun setAvailableLanguages(availableLanguages: List<String>) {
        this.availableLanguages = availableLanguages
        this.filteredLanguages = mutableStateOf(availableLanguages)
        this.searchResults = mutableStateOf(filteredLanguages.value)
    }
}