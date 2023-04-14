package com.example.busybee.ui.login.presenter

interface LoginPresenterInterface {
    fun <T> logIn(userName: String, password: String)
    fun saveToken (token : String)
    fun saveExpirationDate (expirationDate : String)
}