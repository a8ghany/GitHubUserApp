package com.gnacoding.submissionbfaa.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gnacoding.submissionbfaa.data.data_source.local.UserDao
import com.gnacoding.submissionbfaa.data.data_source.local.UserDatabase
import com.gnacoding.submissionbfaa.data.data_source.remote.ApiConfig
import com.gnacoding.submissionbfaa.data.data_source.remote.ApiService
import com.gnacoding.submissionbfaa.data.data_store.UserDataStore
import com.gnacoding.submissionbfaa.domain.model.SearchResponse
import com.gnacoding.submissionbfaa.domain.model.User
import com.gnacoding.submissionbfaa.domain.repository.UserRepository
import com.gnacoding.submissionbfaa.domain.utils.NetworkResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImpl(application: Application) : UserRepository {

    private val apiService: ApiService = ApiConfig.getApiService()
    private val userDao: UserDao
    private val dataStore: UserDataStore

    init {
        val db = UserDatabase.getInstance(application)
        userDao = db.userDao()
        dataStore = UserDataStore.getInstance(application)
    }

    override fun searchUser(query: String): LiveData<NetworkResult<List<User>>> {
        val listUser = MutableLiveData<NetworkResult<List<User>>>()

        listUser.postValue(NetworkResult.Loading())
        apiService.searchUser(query = query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>,
            ) {
                val resultList = response.body()?.items
                if (resultList.isNullOrEmpty()) {
                    listUser.postValue(NetworkResult.Error(message = null))
                } else {
                    listUser.postValue(NetworkResult.Success(data = resultList))
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                listUser.postValue(NetworkResult.Error(message = t.message))
            }
        })
        return listUser
    }

    override suspend fun getDetailUser(username: String): LiveData<NetworkResult<User>> {
        val user = MutableLiveData<NetworkResult<User>>()

        if (userDao.getDetailUser(username = username) != null) {
            user.postValue(NetworkResult.Success(
                data = userDao.getDetailUser(username = username))
            )
        } else {
            apiService.getDetailUser(username).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val result = response.body()
                    user.postValue(NetworkResult.Success(data = result))
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    user.postValue(NetworkResult.Error(message = t.message))
                }
            })
        }
        return user
    }

    override suspend fun insertFavoriteUser(user: User) {
        userDao.insertFavoriteUser(user = user)
    }

    override suspend fun deleteFavoriteUser(user: User) {
        userDao.deleteFavoriteUser(user = user)
    }

    override suspend fun getFavoriteList(): LiveData<NetworkResult<List<User>>> {
        val listFavorite = MutableLiveData<NetworkResult<List<User>>>()

        listFavorite.postValue(NetworkResult.Loading())
        if (userDao.getFavoriteList().isEmpty()) {
            listFavorite.postValue(NetworkResult.Error(message = null))
        } else {
            listFavorite.postValue(
                NetworkResult.Success(data = userDao.getFavoriteList())
            )
        }

        return listFavorite
    }

    override fun getFollowers(username: String): LiveData<NetworkResult<List<User>>> {
        val listUser = MutableLiveData<NetworkResult<List<User>>>()

        listUser.postValue(NetworkResult.Loading())
        apiService.getFollowers(username).enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>,
            ) {
                val resultList = response.body()
                if (resultList.isNullOrEmpty()) {
                    listUser.postValue(NetworkResult.Error(message = null))
                } else {
                    listUser.postValue(NetworkResult.Success(data = resultList))
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                listUser.postValue(NetworkResult.Error(message = t.message))
            }
        })
        return listUser
    }

    override fun getFollowing(username: String): LiveData<NetworkResult<List<User>>> {
        val listUser = MutableLiveData<NetworkResult<List<User>>>()

        listUser.postValue(NetworkResult.Loading())
        apiService.getFollowing(username).enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>,
            ) {
                val resultList = response.body()
                if (resultList.isNullOrEmpty()) {
                    listUser.postValue(NetworkResult.Error(message = null))
                } else {
                    listUser.postValue(NetworkResult.Success(data = resultList))
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                listUser.postValue(NetworkResult.Error(message = t.message))
            }
        })
        return listUser
    }

    override fun getThemeSetting() = dataStore.getThemeSetting()

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) =
        dataStore.saveThemeSetting(isDarkModeActive)
}