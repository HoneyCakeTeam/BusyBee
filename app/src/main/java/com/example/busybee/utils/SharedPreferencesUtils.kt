package com.example.busybee.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtils {
    private var sharedPreferences: SharedPreferences? = null
    private const val SHARED_PREFERENCES_NAME = "MySharedPreferences"
    private const val KEY_TOKEN = "keyToken"

    fun initPreferencesUtil(context: Context) {
        sharedPreferences = context.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    var token: String?
        get() = sharedPreferences!!.getString(KEY_TOKEN, null)
        set(token) {
            sharedPreferences!!.edit().putString(KEY_TOKEN, token).apply()
        }
}