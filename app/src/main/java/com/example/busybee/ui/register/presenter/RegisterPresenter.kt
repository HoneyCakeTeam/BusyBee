package com.example.busybee.ui.register.presenter

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.ui.register.view.RegisterViewInterface


class RegisterPresenter(
    private val repository: RepositoryInterface
) :
    RegisterPresenterInterface {
    override fun <T> signUp(
        userName: String, password: String, onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        repository.signUp(userName, password, onSuccessCallback, onFailureCallback)
    }

}