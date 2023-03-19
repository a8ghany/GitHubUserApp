package com.gnacoding.submissionbfaa.presentation.screen.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gnacoding.submissionbfaa.data.repository.UserRepositoryImpl
import com.gnacoding.submissionbfaa.domain.model.User
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepositoryImpl(application = application)

    suspend fun getDetailUser(username: String) = repository.getDetailUser(username = username)

    fun insertFavoriteUser(user: User) {
        viewModelScope.launch {
            repository.insertFavoriteUser(user = user)
        }
    }

    fun deleteFavoriteUser(user: User) {
        viewModelScope.launch {
            repository.deleteFavoriteUser(user = user)
        }
    }
}