package com.gnacoding.submissionbfaa.data.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.gnacoding.submissionbfaa.utils.Constants.DATA_STORE_NAME
import com.gnacoding.submissionbfaa.utils.Constants.THEME_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStore(private val context: Context) {

    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = DATA_STORE_NAME
    )

    fun getThemeSetting(): Flow<Boolean> {
        return context.userPreferencesDataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: UserDataStore? = null

        fun getInstance(context: Context): UserDataStore {
            return INSTANCE ?: synchronized(this) {
                val instance = UserDataStore(context)
                INSTANCE = instance
                instance
            }
        }
    }
}