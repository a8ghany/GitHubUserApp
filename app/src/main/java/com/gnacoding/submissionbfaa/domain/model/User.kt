package com.gnacoding.submissionbfaa.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user")
data class User(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = 0,

    @ColumnInfo(name = "login")
    var login: String? = "",

    @ColumnInfo(name = "avatar_url")
    var avatar_url: String? = "",

    @ColumnInfo(name = "name")
    var name: String? = "",

    @ColumnInfo(name = "company")
    var company: String? = "",

    @ColumnInfo(name = "location")
    var location: String? = "",

    @ColumnInfo(name = "public_repos")
    var public_repos: String? = "",

    @ColumnInfo(name = "followers")
    var followers: String? = "",

    @ColumnInfo(name = "following")
    var following: String? = "",

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean? = false
): Parcelable
