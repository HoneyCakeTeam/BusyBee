package com.example.busybee.ui.register.presenter

import com.example.busybee.data.source.RemoteDataSourceInterface


class RegisterPresenter(
    private val repository: RemoteDataSourceInterface
) :
    RegisterPresenterInterface {
    override fun <T> signUp(
        userName: String, password: String, onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        repository.signUp(userName, password, onSuccessCallback, onFailureCallback)
    }

}