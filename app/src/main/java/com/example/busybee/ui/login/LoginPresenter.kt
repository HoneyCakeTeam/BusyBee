package com.example.busybee.ui.login

import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.LoginResponseValue
import com.example.busybee.ui.login.LoginView

class LoginPresenter(
    private val repository: Repository, private val loginViewInterface: LoginView
) {
    fun logIn(userName: String, password: String) {

        repository.logIn(userName, password,::onLoginSuccess,::onLoginFailed)

    }

    private fun onLoginSuccess(response: BaseResponse<LoginResponseValue>) {
        loginViewInterface.onLoginSuccess(response)
    }

    private fun onLoginFailed(error: Throwable) {
        loginViewInterface.onLoginFailed(error)
    }

    fun saveTokenInShared(token: String) {
        repository.saveToken(token)
    }

}