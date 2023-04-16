package com.example.busybee.ui.register.presenter

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.SignUpResponseValue
import com.example.busybee.ui.register.view.RegisterViewInterface

class RegisterPresenter(
    private val repository: RepositoryInterface,
    private val registerViewInterface: RegisterViewInterface
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