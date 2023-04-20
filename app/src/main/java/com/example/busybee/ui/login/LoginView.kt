package com.example.busybee.ui.login

interface LoginView {
    fun goToHome()
    fun showErrorMsg(error: Throwable)
    fun showValidationError(usernameErrorMessage: String?, passwordErrorMessage: String?)
    fun hideValidationErrorThenLogin(username: String, password: String)

}

