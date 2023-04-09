package com.example.busybee.ui.login.view

interface LoginViewInterface {

    fun <T> logIn(
        userName: String, password: String, onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )
}