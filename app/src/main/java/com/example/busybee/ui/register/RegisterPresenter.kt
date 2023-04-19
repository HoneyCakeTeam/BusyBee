package com.example.busybee.ui.register

import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.LoginResponseValue
import com.example.busybee.data.models.SignUpResponseValue

class RegisterPresenter(
    private val repository: Repository,
    private val view: RegisterView
) {
    fun signUp(userName: String, password: String) {
        repository.signUp(userName, password, ::onRegisterSuccess, ::onRegisterFailed)
    }

    private fun onRegisterSuccess(response: BaseResponse<SignUpResponseValue>) {
        view.login()
    }

    private fun onRegisterFailed(error: Throwable) {
        view.showErrorMsg(error)
    }

    fun logIn(userName: String, password: String) {
        repository.logIn(userName, password, ::onLoginSuccess, ::onLoginFailed)
    }

    private fun onLoginSuccess(response: BaseResponse<LoginResponseValue>) {
        saveToken(response.value.token)
        view.goToHome()
    }

    private fun onLoginFailed(error: Throwable) {
        view.showErrorMsg(error)
    }

    private fun saveToken(token: String) {
        repository.saveToken(token)
    }
}