package com.gnacoding.submissionbfaa.data.remote.retrofit

import com.gnacoding.submissionbfaa.data.remote.response.SearchResponse
import com.gnacoding.submissionbfaa.data.model.UserEntity
import com.gnacoding.submissionbfaa.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token ${Constants.GITHUB_TOKEN}")
    fun searchUser(@Query("q") query: String): Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${Constants.GITHUB_TOKEN}")
    fun getUserDetail(@Path("username") username: String): Call<UserEntity>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${Constants.GITHUB_TOKEN}")
    fun getFollowers(@Path("username") username: String): Call<List<UserEntity>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${Constants.GITHUB_TOKEN}")
    fun getFollowing(@Path("username") username: String): Call<List<UserEntity>>
}