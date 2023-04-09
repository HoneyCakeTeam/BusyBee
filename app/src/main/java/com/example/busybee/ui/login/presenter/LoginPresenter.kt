package com.example.busybee.ui.login.presenter

import com.example.busybee.data.Repository
import com.example.busybee.ui.login.view.LoginViewInterface


class LoginPresenter(val loginViewInterface : LoginViewInterface, val repository : Repository) : LoginPresenterInterface {
    override fun <T> logIn(userName: String, password: String, onSuccessCallback: (response: T) -> Unit ,
                           onFailureCallback: (error: Throwable) -> Unit ){
        repository.logIn<T>(userName , password , onSuccessCallback , onFailureCallback)

        loginViewInterface.logIn<T>(userName,password , onSuccessCallback, onFailureCallback)
    }

}