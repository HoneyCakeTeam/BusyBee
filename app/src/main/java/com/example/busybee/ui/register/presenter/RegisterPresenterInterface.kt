package com.example.busybee.ui.register.presenter


interface RegisterPresenterInterface {
    fun <T> signUp(
        userName: String, password: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )
}