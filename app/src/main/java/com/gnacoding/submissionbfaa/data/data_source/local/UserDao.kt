package com.gnacoding.submissionbfaa.data.data_source.local

import androidx.room.*
import com.gnacoding.submissionbfaa.domain.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE login = :username")
    suspend fun getDetailUser(username: String): User?

    @Query("SELECT * FROM user ORDER BY login ASC")
    suspend fun getFavoriteList(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: User)

    @Delete
    suspend fun deleteFavoriteUser(user: User)
}