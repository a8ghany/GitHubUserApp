package com.gnacoding.submissionbfaa.presentation.screen.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.gnacoding.submissionbfaa.data.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepositoryImpl(application)

    fun getThemeSetting() = repository.getThemeSetting().asLiveData(Dispatchers.IO)
}