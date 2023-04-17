package com.example.busybee.ui.register

import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.SignUpResponseValue

class RegisterPresenter(
    private val repository: Repository,
    private val registerViewInterface: RegisterView
) {
    fun signUp(userName: String, password: String) {
        repository.signUp(userName, password, ::onRegisterSuccess, ::onRegisterFailed)
    }

    private fun onRegisterSuccess(response: BaseResponse<SignUpResponseValue>) {
        registerViewInterface.onRegisterSuccess(response)
    }

    private fun onRegisterFailed(error: Throwable) {
        registerViewInterface.onRegisterFailed(error)
    }

}