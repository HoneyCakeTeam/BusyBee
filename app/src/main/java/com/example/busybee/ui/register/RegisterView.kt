package com.example.busybee.ui.register

interface RegisterView {
    fun goToHome()
    fun login()
    fun showErrorMsg(error: Throwable)
    fun showValidationError(
        usernameErrorMessage: String?,
        passwordErrorMessage: String?,
        confirmPasswordErrorMessage: String?
    )

    fun hideValidationErrorThenRegister(userName: String, password: String)

}