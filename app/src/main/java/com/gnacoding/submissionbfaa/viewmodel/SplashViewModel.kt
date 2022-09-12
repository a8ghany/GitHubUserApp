package com.gnacoding.submissionbfaa.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.gnacoding.submissionbfaa.data.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SettingsRepository(application)

    fun getThemeSetting() = repository.getThemeSetting().asLiveData(Dispatchers.IO)
}