package com.gnacoding.submissionbfaa.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gnacoding.submissionbfaa.data.Result
import com.gnacoding.submissionbfaa.data.model.UserEntity
import com.gnacoding.submissionbfaa.data.local.UserDao
import com.gnacoding.submissionbfaa.data.local.UserDatabase

class FavoriteRepository(application: Application) {

    private val userDao: UserDao

    init {
        val database: UserDatabase = UserDatabase.getInstance(application)
        userDao = database.userDao()
    }

    suspend fun getFavoriteList(): LiveData<Result<List<UserEntity>>> {
        val listFavorite = MutableLiveData<Result<List<UserEntity>>>()

        listFavorite.postValue(Result.Loading())
        if (userDao.getFavoriteList().isEmpty()) {
            listFavorite.postValue(Result.Error(null))
        } else {
            listFavorite.postValue(Result.Success(userDao.getFavoriteList()))
        }

        return listFavorite
    }
}