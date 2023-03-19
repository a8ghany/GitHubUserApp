package com.gnacoding.submissionbfaa.presentation.screen.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gnacoding.submissionbfaa.data.repository.UserRepositoryImpl

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepositoryImpl(application = application)

    suspend fun getFavoriteList() = repository.getFavoriteList()
}