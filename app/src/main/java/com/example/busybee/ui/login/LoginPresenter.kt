package com.example.busybee.ui.login

import android.util.Log
import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.LoginResponseValue

class LoginPresenter(
    private val repository: Repository, private val view: LoginView
) {
    fun logIn(userName: String, password: String) {
        try {
            repository.logIn(userName, password, ::onLoginSuccess, ::onLoginFailed)

        } catch (error: Throwable) {
            Log.e("TAG", "No Internet logIn: ")
        }

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