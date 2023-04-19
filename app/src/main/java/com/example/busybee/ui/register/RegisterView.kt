package com.example.busybee.ui.register

interface RegisterView {
    fun goToHome()
    fun login()
    fun showErrorMsg(error: Throwable)

}