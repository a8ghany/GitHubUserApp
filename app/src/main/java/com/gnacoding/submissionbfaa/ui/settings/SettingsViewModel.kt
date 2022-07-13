package com.gnacoding.submissionbfaa.ui.settings

import android.app.Application
import androidx.lifecycle.*
import com.gnacoding.submissionbfaa.data.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsRepository = SettingsRepository(application)

    fun getThemeSetting() = settingsRepository.getThemeSetting().asLiveData(Dispatchers.IO)

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            settingsRepository.saveThemeSetting(isDarkModeActive)
        }
    }
}