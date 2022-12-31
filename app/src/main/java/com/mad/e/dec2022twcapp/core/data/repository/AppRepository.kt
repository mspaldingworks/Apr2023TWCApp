package com.mad.e.dec2022twcapp.core.data.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val dataUserPreferences: DataStore<Preferences>
) {

    suspend fun saveHasBeenOpenedPreference() {
        dataUserPreferences.edit {
            it[APP_OPENED] = true
        }
    }

    fun hasBeenOpened(): Flow<Boolean> =
        dataUserPreferences.data.map { preferences ->
            preferences[APP_OPENED] ?: false
        }

    companion object {
        private val APP_OPENED = booleanPreferencesKey("APP_OPENED")
    }
}
