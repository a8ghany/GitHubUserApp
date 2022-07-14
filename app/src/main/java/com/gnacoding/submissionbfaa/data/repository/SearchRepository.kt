package com.gnacoding.submissionbfaa.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gnacoding.submissionbfaa.utils.Result
import com.gnacoding.submissionbfaa.data.model.UserEntity
import com.gnacoding.submissionbfaa.data.remote.response.SearchResponse
import com.gnacoding.submissionbfaa.data.remote.retrofit.ApiService
import com.gnacoding.submissionbfaa.data.remote.retrofit.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository {

    private val apiService: ApiService = RetrofitConfig.getApiService()

    fun searchUser(query: String): LiveData<Result<List<UserEntity>>> {
        val listUser = MutableLiveData<Result<List<UserEntity>>>()

        listUser.postValue(Result.Loading())
        apiService.searchUser(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val list = response.body()?.items
                if (list.isNullOrEmpty()) {
                    listUser.postValue(Result.Error(null))
                } else {
                    listUser.postValue(Result.Success(list))
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                listUser.postValue(Result.Error(t.message))
            }
        })
        return listUser
    }
}