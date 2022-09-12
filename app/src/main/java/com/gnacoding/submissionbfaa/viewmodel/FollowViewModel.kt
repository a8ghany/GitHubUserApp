package com.gnacoding.submissionbfaa.viewmodel

import androidx.lifecycle.ViewModel
import com.gnacoding.submissionbfaa.data.repository.FollowRepository

class FollowViewModel : ViewModel() {

    private val followRepository = FollowRepository()

    fun getFollowers(username: String) = followRepository.getFollowers(username)

    fun getFollowing(username: String) = followRepository.getFollowing(username)
}