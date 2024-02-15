package com.synrgyacademy.data.local.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.synrgyacademy.common.Constants.EMAIL_KEY
import com.synrgyacademy.common.Constants.LOGIN_KEY
import com.synrgyacademy.common.Constants.NAME_KEY
import com.synrgyacademy.common.Constants.PREFS_NOTIFICATION
import com.synrgyacademy.common.Constants.PREF_BAGASI_FILTER
import com.synrgyacademy.common.Constants.PREF_HIBURAN_FILTER
import com.synrgyacademy.common.Constants.PREF_MAKANAN_FILTER
import com.synrgyacademy.common.Constants.PREF_PRICE_FILTER_END
import com.synrgyacademy.common.Constants.PREF_PRICE_FILTER_START
import com.synrgyacademy.common.Constants.PREF_REFUNDABLE_FILTER
import com.synrgyacademy.common.Constants.PREF_RESCHEDULE_FILTER
import com.synrgyacademy.common.Constants.PREF_STOPKONTAK_FILTER
import com.synrgyacademy.common.Constants.PREF_TIME_FILTER
import com.synrgyacademy.common.Constants.PREF_WIFI_FILTER
import com.synrgyacademy.common.Constants.TOKEN_KEY
import com.synrgyacademy.domain.model.airport.FilterDataModel
import com.synrgyacademy.domain.model.auth.UserDataDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SessionManager(private val dataStore: DataStore<Preferences>) {

    companion object {
        val KEY_LOGIN = booleanPreferencesKey(LOGIN_KEY)
        val KEY_NAME = stringPreferencesKey(NAME_KEY)
        val KEY_TOKEN = stringPreferencesKey(TOKEN_KEY)
        val KEY_EMAIL = stringPreferencesKey(EMAIL_KEY)

        val KEY_TIME_FILTER = stringPreferencesKey(PREF_TIME_FILTER)
        val KEY_PRICE_FILTER_START = floatPreferencesKey(PREF_PRICE_FILTER_START)
        val KEY_PRICE_FILTER_END = floatPreferencesKey(PREF_PRICE_FILTER_END)
        val KEY_BAGASI_FILTER = booleanPreferencesKey(PREF_BAGASI_FILTER)
        val KEY_HIBURAN_FILTER = booleanPreferencesKey(PREF_HIBURAN_FILTER)
        val KEY_MAKANAN_FILTER = booleanPreferencesKey(PREF_MAKANAN_FILTER)
        val KEY_STOPKONTAK_FILTER = booleanPreferencesKey(PREF_STOPKONTAK_FILTER)
        val KEY_WIFI_FILTER = booleanPreferencesKey(PREF_WIFI_FILTER)
        val KEY_REFUNDABLE_FILTER = booleanPreferencesKey(PREF_REFUNDABLE_FILTER)
        val KEY_RESCHEDULE_FILTER = booleanPreferencesKey(PREF_RESCHEDULE_FILTER)

        val KEY_NOTIFICATION = booleanPreferencesKey(PREFS_NOTIFICATION)
    }

    fun isLogin() = dataStore.data.map {
        it[KEY_LOGIN] ?: false
    }

    suspend fun saveNotification(isActive: Boolean) {
        dataStore.edit {
            it[KEY_NOTIFICATION] = isActive
        }
    }

    fun getNotification(): Flow<Boolean> = dataStore.data.map {
        it[KEY_NOTIFICATION] ?: true
    }

    suspend fun createSession() {
        dataStore.edit {
            it[KEY_LOGIN] = true
        }
    }

    suspend fun savedFilter(filterDataModel: FilterDataModel) {
        dataStore.edit {
            it[KEY_TIME_FILTER] = filterDataModel.time ?: ""
            it[KEY_PRICE_FILTER_START] = filterDataModel.priceStart
            it[KEY_PRICE_FILTER_END] = filterDataModel.priceEnd
            it[KEY_BAGASI_FILTER] = filterDataModel.bagasi ?: false
            it[KEY_HIBURAN_FILTER] = filterDataModel.hiburan ?: false
            it[KEY_MAKANAN_FILTER] = filterDataModel.makanan ?: false
            it[KEY_STOPKONTAK_FILTER] = filterDataModel.stopkontak ?: false
            it[KEY_WIFI_FILTER] = filterDataModel.wifi ?: false
            it[KEY_REFUNDABLE_FILTER] = filterDataModel.refundable ?: false
            it[KEY_RESCHEDULE_FILTER] = filterDataModel.reschedule ?: false
        }
    }

    suspend fun deletedFilter() {
        dataStore.edit {
            it.remove(KEY_TIME_FILTER)
            it.remove(KEY_PRICE_FILTER_START)
            it.remove(KEY_PRICE_FILTER_END)
            it.remove(KEY_BAGASI_FILTER)
            it.remove(KEY_HIBURAN_FILTER)
            it.remove(KEY_MAKANAN_FILTER)
            it.remove(KEY_STOPKONTAK_FILTER)
            it.remove(KEY_WIFI_FILTER)
            it.remove(KEY_REFUNDABLE_FILTER)
            it.remove(KEY_RESCHEDULE_FILTER)
        }
    }

    fun getSavedFilter(): Flow<FilterDataModel> = dataStore.data.map {
        val time = it[KEY_TIME_FILTER].orEmpty()
        val priceStart = it[KEY_PRICE_FILTER_START] ?: 0f
        val priceEnd = it[KEY_PRICE_FILTER_END] ?: 10000000f
        val bagasi = it[KEY_BAGASI_FILTER] ?: false
        val hiburan = it[KEY_HIBURAN_FILTER] ?: false
        val makanan = it[KEY_MAKANAN_FILTER] ?: false
        val stopkontak = it[KEY_STOPKONTAK_FILTER] ?: false
        val wifi = it[KEY_WIFI_FILTER] ?: false
        val refundable = it[KEY_REFUNDABLE_FILTER] ?: false
        val reschedule = it[KEY_RESCHEDULE_FILTER] ?: false
        FilterDataModel(
            time = time,
            priceStart = priceStart,
            priceEnd = priceEnd,
            bagasi = bagasi,
            hiburan = hiburan,
            makanan = makanan,
            stopkontak = stopkontak,
            wifi = wifi,
            refundable = refundable,
            reschedule = reschedule
        )
    }

    fun getUser(): Flow<UserDataDataModel> = dataStore.data.map {
        val email = it[KEY_EMAIL].orEmpty()
        val token = it[KEY_TOKEN].orEmpty()
        UserDataDataModel(
            email = email,
            token = token
        )
    }

    suspend fun saveUser(userDataDataModel: UserDataDataModel) {
        dataStore.edit {
            it[KEY_EMAIL] = userDataDataModel.email
            it[KEY_TOKEN] = userDataDataModel.token
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it.remove(KEY_LOGIN)
            it.remove(KEY_NAME)
            it.remove(KEY_TOKEN)
            it.remove(KEY_EMAIL)
        }
    }
}