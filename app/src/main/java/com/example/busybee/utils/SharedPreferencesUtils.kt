package com.example.busybee.utils

import android.content.Context
import android.content.SharedPreferences
import java.time.LocalDate

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

    var expirationDate: String?
        get() = sharedPreferences?.getString(EXPIRATION_DATE_KEY, null)
        set(value) {
            sharedPreferences?.edit()?.putString(EXPIRATION_DATE_KEY, value)?.apply()
        }

    private fun clearToken() = sharedPreferences?.edit()?.remove(USER_TOKEN)?.apply()

    fun isTokenExpired(): Boolean {
        val expirationDateString = expirationDate ?: return false

        val expirationDate = LocalDate.parse(expirationDateString)
        val currentDate = LocalDate.now()

        return currentDate.isAfter(expirationDate)
    }
}