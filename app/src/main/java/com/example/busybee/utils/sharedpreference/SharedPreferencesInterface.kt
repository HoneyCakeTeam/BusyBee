package com.example.busybee.utils.sharedpreference

interface SharedPreferencesInterface {
    var token: String?

    fun clearToken(): Unit?
}