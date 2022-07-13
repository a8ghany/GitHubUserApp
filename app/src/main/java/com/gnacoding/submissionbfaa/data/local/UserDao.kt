package com.gnacoding.submissionbfaa.data.local

import androidx.room.*
import com.gnacoding.submissionbfaa.data.model.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE login = :username")
    suspend fun getDetailUser(username: String): UserEntity?

    @Query("SELECT * FROM user ORDER BY login ASC")
    suspend fun getFavoriteList(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: UserEntity)

    @Delete
    suspend fun deleteFavoriteUser(user: UserEntity)
}