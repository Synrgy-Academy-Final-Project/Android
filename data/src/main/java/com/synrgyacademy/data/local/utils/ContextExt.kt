package com.synrgyacademy.data.local.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.synrgyacademy.common.Constants.PREF_NAME

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREF_NAME)