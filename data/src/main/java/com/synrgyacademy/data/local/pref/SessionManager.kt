package com.synrgyacademy.data.local.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.synrgyacademy.common.Constants.LOGIN_KEY
import com.synrgyacademy.common.Constants.NAME_KEY
import com.synrgyacademy.common.Constants.TOKEN_KEY
import com.synrgyacademy.domain.model.airport.BookerData
import com.synrgyacademy.domain.model.auth.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SessionManager(private val dataStore: DataStore<Preferences>) {

    companion object {
        val KEY_LOGIN = booleanPreferencesKey(LOGIN_KEY)
        val KEY_NAME = stringPreferencesKey(NAME_KEY)
        val KEY_TOKEN = stringPreferencesKey(TOKEN_KEY)

        val KEY_BOOKER_NAME = stringPreferencesKey("booker_name")
        val KEY_BOOKER_PHONE = stringPreferencesKey("booker_phone")
        val KEY_BOOKER_EMAIL = stringPreferencesKey("booker_email")
    }

    fun isLogin() = dataStore.data.map {
        it[KEY_LOGIN] ?: false
    }

    suspend fun createSession() {
        dataStore.edit {
            it[KEY_LOGIN] = true
        }
    }

    fun getUser(): Flow<UserData> = dataStore.data.map {
        val name = it[KEY_NAME].orEmpty()
        val token = it[KEY_TOKEN].orEmpty()
        UserData(
            name = name,
            token = token
        )
    }

    fun getPrefsBooker(): Flow<BookerData> = dataStore.data.map {
        val name = it[KEY_BOOKER_NAME].orEmpty()
        val phone = it[KEY_BOOKER_PHONE].orEmpty()
        val email = it[KEY_BOOKER_EMAIL].orEmpty()
        BookerData(
            name = name,
            phone = phone,
            email = email
        )
    }

    suspend fun savePrefsBooker(bookerData: BookerData) {
        dataStore.edit {
            it[KEY_BOOKER_NAME] = bookerData.name
            it[KEY_BOOKER_PHONE] = bookerData.phone
            it[KEY_BOOKER_EMAIL] = bookerData.email
        }
    }

    suspend fun saveUser(userData: UserData) {
        dataStore.edit {
            it[KEY_NAME] = userData.name
            it[KEY_TOKEN] = userData.token
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it.clear()
        }
    }
}