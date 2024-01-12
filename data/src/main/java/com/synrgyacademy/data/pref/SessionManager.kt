package com.synrgyacademy.data.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.synrgyacademy.common.Constants.EMAIL_KEY
import com.synrgyacademy.common.Constants.LOGIN_KEY
import com.synrgyacademy.common.Constants.NAME_KEY
import com.synrgyacademy.common.Constants.TOKEN_KEY
import com.synrgyacademy.domain.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionManager(private val dataStore: DataStore<Preferences>) {

    companion object {
        val KEY_LOGIN = booleanPreferencesKey(LOGIN_KEY)
        val KEY_EMAIL = stringPreferencesKey(EMAIL_KEY)
        val KEY_NAME = stringPreferencesKey(NAME_KEY)
        val KEY_TOKEN = stringPreferencesKey(TOKEN_KEY)
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
        val email = it[KEY_EMAIL].orEmpty()
        val token = it[KEY_TOKEN].orEmpty()
        UserData(
            name = name,
            email = email,
            token = token
        )
    }

    suspend fun saveUser(userData: UserData) {
        dataStore.edit {
            it[KEY_NAME] = userData.name
            it[KEY_EMAIL] = userData.email
            it[KEY_TOKEN] = userData.token
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it.clear()
        }
    }

}