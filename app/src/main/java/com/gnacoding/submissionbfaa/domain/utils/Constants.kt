package com.gnacoding.submissionbfaa.domain.utils

import androidx.datastore.preferences.core.booleanPreferencesKey

object Constants {
    const val GITHUB_TOKEN = "put_your_github_token_here"
    const val BASE_URL = "https://api.github.com/"

    const val DATA_STORE_NAME = "settings"
    val THEME_KEY = booleanPreferencesKey("theme_setting")

    const val SPLASH_TIME_DELAY = 3000L

    const val USERS_DB = "users_db"

    const val ARG_USERNAME = "username"
}
