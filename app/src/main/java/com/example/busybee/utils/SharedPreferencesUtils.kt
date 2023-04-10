package com.example.busybee.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtils {
    private var sharedPreferences: SharedPreferences? = null
    private const val SHARED_PREFERENCES_NAME = "MySharedPreferences"
    private const val USER_TOKEN = "keyToken"
    private const val EXPIRATION_DATE_KEY = "expirationDate"

    fun initPreferencesUtil(context: Context) {
        sharedPreferences = context.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    var token: String?
        get() = sharedPreferences?.getString(USER_TOKEN, null)
        set(value) {
            token ?: clearToken()
            sharedPreferences?.edit()?.putString(USER_TOKEN, value)?.apply()
        }

    var expirationDate: Long?
        get() = sharedPreferences?.getLong(EXPIRATION_DATE_KEY, Long.MIN_VALUE)
        set(value) {
            sharedPreferences?.edit()?.putLong(EXPIRATION_DATE_KEY, value!!)?.apply()
        }

    private fun clearToken() = sharedPreferences?.edit()?.remove(USER_TOKEN)?.apply()

    fun isTokenExpired(): Boolean = expirationDate!! <= System.currentTimeMillis()
}