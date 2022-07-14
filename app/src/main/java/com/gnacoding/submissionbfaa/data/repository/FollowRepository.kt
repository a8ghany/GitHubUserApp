package com.gnacoding.submissionbfaa.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gnacoding.submissionbfaa.utils.Result
import com.gnacoding.submissionbfaa.data.model.UserEntity
import com.gnacoding.submissionbfaa.data.remote.retrofit.ApiService
import com.gnacoding.submissionbfaa.data.remote.retrofit.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowRepository {

    private val apiService: ApiService = RetrofitConfig.getApiService()

    fun getFollowers(username: String): LiveData<Result<List<UserEntity>>> {
        val listUser = MutableLiveData<Result<List<UserEntity>>>()

        listUser.postValue(Result.Loading())
        apiService.getFollowers(username).enqueue(object : Callback<List<UserEntity>> {
            override fun onResponse(
                call: Call<List<UserEntity>>,
                response: Response<List<UserEntity>>
            ) {
                val list = response.body()
                if (list.isNullOrEmpty()) {
                    listUser.postValue(Result.Error(null))
                } else {
                    listUser.postValue(Result.Success(list))
                }
            }

            override fun onFailure(call: Call<List<UserEntity>>, t: Throwable) {
                listUser.postValue(Result.Error(t.message))
            }
        })
        return listUser
    }

    fun getFollowing(username: String): LiveData<Result<List<UserEntity>>> {
        val listUser = MutableLiveData<Result<List<UserEntity>>>()

        listUser.postValue(Result.Loading())
        apiService.getFollowing(username).enqueue(object : Callback<List<UserEntity>> {
            override fun onResponse(
                call: Call<List<UserEntity>>,
                response: Response<List<UserEntity>>
            ) {
                val list = response.body()
                if (list.isNullOrEmpty()) {
                    listUser.postValue(Result.Error(null))
                } else {
                    listUser.postValue(Result.Success(list))
                }
            }

            override fun onFailure(call: Call<List<UserEntity>>, t: Throwable) {
                listUser.postValue(Result.Error(t.message))
            }
        })
        return listUser
    }
}