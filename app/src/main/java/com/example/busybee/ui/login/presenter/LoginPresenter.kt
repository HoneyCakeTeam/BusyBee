package com.example.busybee.ui.login.presenter

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.data.models.LoginResponse
import com.example.busybee.ui.login.view.LoginViewInterface

class LoginPresenter(
    private val repository: RepositoryInterface,
    private val view : LoginViewInterface
) :
    LoginPresenterInterface {
    override fun <T> logIn(userName: String, password: String) {
        repository.logIn(userName, password, ::onSuccessCallback, ::onFailureCallback)
    }

     fun onSuccessCallback(response: LoginResponse) {
      view.onSuccessResponse(response)
    }

     fun onFailureCallback(error: Throwable) {
        view.onFailureResponse(error)
    }

    override fun saveToken (token : String){
        repository.saveTokenInShared(token)
    }
    override fun saveExpirationDate (expirationDate : String){
        repository.saveExpirationDateInShared(expirationDate)
    }

}