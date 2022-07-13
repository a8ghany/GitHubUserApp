package com.gnacoding.submissionbfaa.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gnacoding.submissionbfaa.data.model.UserEntity
import com.gnacoding.submissionbfaa.data.repository.DetailRepository
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val detailRepository = DetailRepository(application)

    suspend fun getDetailUser(username: String) = detailRepository.getDetailUser(username)

    fun insertFavoriteUser(user: UserEntity) {
        viewModelScope.launch {
            detailRepository.insertFavoriteUser(user)
        }
    }

    fun deleteFavoriteUser(user: UserEntity) {
        viewModelScope.launch {
            detailRepository.deleteFavoriteUser(user)
        }
    }
}