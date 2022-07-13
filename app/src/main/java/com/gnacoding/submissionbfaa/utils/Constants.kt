package com.gnacoding.submissionbfaa.utils

import androidx.datastore.preferences.core.booleanPreferencesKey

object Constants {
    const val GITHUB_TOKEN = "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"
    const val BASE_URL = "https://api.github.com/"

    const val DATA_STORE_NAME = "settings"
    val THEME_KEY = booleanPreferencesKey("theme_setting")

    const val ARG_USERNAME = "username"
}