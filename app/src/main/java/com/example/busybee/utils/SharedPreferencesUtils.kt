package com.example.busybee.utils

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class SharedPreferencesUtils(context: Context) {
    private var sharedPreferences: SharedPreferences? = null

    var token: String?
        get() = sharedPreferences?.getString(USER_TOKEN, null)
        set(value) {
            token ?: clearToken()
            sharedPreferences?.edit()?.putString(USER_TOKEN, value)?.apply()
        }

    private fun clearToken() = sharedPreferences?.edit()?.remove(USER_TOKEN)?.apply()

    init {
        sharedPreferences = context.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "MySharedPreferences"
        private const val USER_TOKEN = "keyToken"
    }
}