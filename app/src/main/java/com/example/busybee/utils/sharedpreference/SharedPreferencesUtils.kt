package com.example.busybee.utils.sharedpreference

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtils(context: Context) : SharedPreferencesInterface {
    private var sharedPreferences: SharedPreferences? = null

    override var token: String?
        get() = sharedPreferences?.getString(USER_TOKEN, null)
        set(value) {
            token ?: clearToken()
            sharedPreferences?.edit()?.putString(USER_TOKEN, value)?.apply()
        }

    init {
        sharedPreferences = context.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    override fun clearToken() = sharedPreferences?.edit()?.remove(USER_TOKEN)?.apply()

    companion object {
        private const val SHARED_PREFERENCES_NAME = "MySharedPreferences"
        private const val USER_TOKEN = "keyToken"
    }
}