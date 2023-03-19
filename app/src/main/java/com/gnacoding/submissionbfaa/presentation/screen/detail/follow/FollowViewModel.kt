package com.gnacoding.submissionbfaa.presentation.screen.detail.follow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gnacoding.submissionbfaa.data.repository.UserRepositoryImpl

class FollowViewModel(application: Application) : AndroidViewModel(application) {

    private val followRepository = UserRepositoryImpl(application = application)

    fun getFollowers(username: String) = followRepository.getFollowers(username = username)

    fun getFollowing(username: String) = followRepository.getFollowing(username = username)
}