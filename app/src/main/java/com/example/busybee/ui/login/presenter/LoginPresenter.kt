package com.example.busybee.ui.login.presenter

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.LoginResponseValue
import com.example.busybee.ui.login.view.LoginViewInterface

class LoginPresenter(
    private val repository: RepositoryInterface, private val loginViewInterface: LoginViewInterface
) {
    fun <T> logIn(userName: String, password: String) {

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

    fun saveExpirationDateInShared(expirationDate: String) {
        repository.saveExpirationDate(expirationDate)
    }


}