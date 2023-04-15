package cz.fit.cvut.stasikmobile.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class UserProfileSource(
    private val preferences: DataStore<Preferences>
) {
    private val NAME_KEY = stringPreferencesKey("userProfile")
    private val LOGGED_KEY = booleanPreferencesKey("logged")


    fun getName(): Flow<String> {
        return preferences.data
            .map { prefs ->
              prefs[NAME_KEY] ?: ""
            }
            .distinctUntilChanged()
    }

    suspend fun setName(name: String) {
        preferences.edit {
                prefs -> prefs[NAME_KEY] = name
        }
    }


    fun getLogged(): Flow<Boolean> {
        return preferences.data
            .map { prefs ->
                prefs[LOGGED_KEY] ?: false
            }
            .distinctUntilChanged()
    }

    suspend fun setLogged(value: Boolean) {
        preferences.edit {
                prefs -> prefs[LOGGED_KEY] = value
        }
    }

}