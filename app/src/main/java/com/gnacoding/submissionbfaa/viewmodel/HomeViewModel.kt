package com.gnacoding.submissionbfaa.viewmodel

import androidx.lifecycle.ViewModel
import com.gnacoding.submissionbfaa.data.repository.SearchRepository

class HomeViewModel : ViewModel() {

    private val searchRepository = SearchRepository()

    fun searchUser(query: String) = searchRepository.searchUser(query)
}