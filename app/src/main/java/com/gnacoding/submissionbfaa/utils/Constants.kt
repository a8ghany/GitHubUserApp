package com.gnacoding.submissionbfaa.utils

import androidx.datastore.preferences.core.booleanPreferencesKey

object Constants {
    const val GITHUB_TOKEN = "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"
    const val BASE_URL = "https://api.github.com/"

    const val DATA_STORE_NAME = "settings"
    val THEME_KEY = booleanPreferencesKey("theme_setting")

    const val ARG_USERNAME = "username"
}