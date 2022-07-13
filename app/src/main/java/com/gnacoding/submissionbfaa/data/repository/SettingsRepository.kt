package com.gnacoding.submissionbfaa.data.repository

import android.app.Application
import com.gnacoding.submissionbfaa.data.datastore.UserDataStore

class SettingsRepository(application: Application) {

    private val dataStore: UserDataStore

    init {
        dataStore = UserDataStore.getInstance(application)
    }

    fun getThemeSetting() = dataStore.getThemeSetting()

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) =
        dataStore.saveThemeSetting(isDarkModeActive)
}