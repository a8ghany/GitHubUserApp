package com.gnacoding.submissionbfaa.domain.repository

import androidx.lifecycle.LiveData
import com.gnacoding.submissionbfaa.domain.model.User
import com.gnacoding.submissionbfaa.domain.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun searchUser(query: String): LiveData<NetworkResult<List<User>>>


    suspend fun getDetailUser(username: String): LiveData<NetworkResult<User>>

    suspend fun insertFavoriteUser(user: User)

    suspend fun deleteFavoriteUser(user: User)


    suspend fun getFavoriteList(): LiveData<NetworkResult<List<User>>>


    fun getFollowers(username: String): LiveData<NetworkResult<List<User>>>

    fun getFollowing(username: String): LiveData<NetworkResult<List<User>>>


    fun getThemeSetting(): Flow<Boolean>

    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
}