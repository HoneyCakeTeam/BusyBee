package com.example.busybee.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class SharedPreferencesUtils(context: Context) {
    private var sharedPreferences: SharedPreferences? = null

    var token: String?
        get() = sharedPreferences?.getString(USER_TOKEN, null)
        set(value) {
            token ?: clearToken()
            sharedPreferences?.edit()?.putString(USER_TOKEN, value)?.apply()
        }

    private fun clearToken() = sharedPreferences?.edit()?.remove(USER_TOKEN)?.apply()

    fun getTheme(): Int {
        return sharedPreferences!!.getInt(THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    fun setTheme(theme: Int) {
        val editor = sharedPreferences!!.edit()
        editor.putInt(THEME, theme)
        editor.apply()
    }

    init {
        sharedPreferences = context.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "MySharedPreferences"
        private const val USER_TOKEN = "keyToken"
        private const val THEME = "theme"
    }
}