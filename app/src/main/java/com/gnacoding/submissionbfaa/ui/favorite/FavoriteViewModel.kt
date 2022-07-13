package com.gnacoding.submissionbfaa.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gnacoding.submissionbfaa.data.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val favoriteRepository = FavoriteRepository(application)

    suspend fun getFavoriteList() = favoriteRepository.getFavoriteList()
}