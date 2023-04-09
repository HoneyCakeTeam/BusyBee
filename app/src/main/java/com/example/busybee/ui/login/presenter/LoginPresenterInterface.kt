package com.example.busybee.ui.login.presenter

interface LoginPresenterInterface {
    fun <T> logIn(userName: String, password: String, onSuccessCallback: (response: T) -> Unit,
                  onFailureCallback: (error: Throwable) -> Unit )
}