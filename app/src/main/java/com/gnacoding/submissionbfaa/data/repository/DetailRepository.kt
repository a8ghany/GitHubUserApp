package com.gnacoding.submissionbfaa.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gnacoding.submissionbfaa.data.Result
import com.gnacoding.submissionbfaa.data.model.UserEntity
import com.gnacoding.submissionbfaa.data.local.UserDao
import com.gnacoding.submissionbfaa.data.local.UserDatabase
import com.gnacoding.submissionbfaa.data.remote.retrofit.ApiService
import com.gnacoding.submissionbfaa.data.remote.retrofit.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRepository(application: Application) {

    private val apiService: ApiService = RetrofitConfig.getApiService()
    private val userDao: UserDao

    init {
        val db = UserDatabase.getInstance(application)
        userDao = db.userDao()
    }

    suspend fun getDetailUser(username: String): LiveData<Result<UserEntity>> {
        val user = MutableLiveData<Result<UserEntity>>()

        if (userDao.getDetailUser(username) != null) {
            user.postValue(Result.Success(userDao.getDetailUser(username)))
        } else {
            apiService.getUserDetail(username).enqueue(object : Callback<UserEntity> {
                override fun onResponse(call: Call<UserEntity>, response: Response<UserEntity>) {
                    val result = response.body()
                    user.postValue(Result.Success(result))
                }

                override fun onFailure(call: Call<UserEntity>, t: Throwable) {
                    user.postValue(Result.Error(t.message))
                }
            })
        }
        return user
    }

    suspend fun insertFavoriteUser(user: UserEntity) = userDao.insertFavoriteUser(user)

    suspend fun deleteFavoriteUser(user: UserEntity) = userDao.deleteFavoriteUser(user)
}