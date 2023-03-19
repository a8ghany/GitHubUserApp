package com.gnacoding.submissionbfaa.presentation.screen.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gnacoding.submissionbfaa.data.repository.UserRepositoryImpl

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepositoryImpl(application = application)

    fun searchUser(query: String) = repository.searchUser(query = query)
}