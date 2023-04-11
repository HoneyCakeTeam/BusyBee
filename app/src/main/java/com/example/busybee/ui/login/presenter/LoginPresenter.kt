package com.example.busybee.ui.login.presenter

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.ui.login.view.LoginViewInterface

class LoginPresenter(
    val loginViewInterface: LoginViewInterface,
    private val repository: RepositoryInterface
) :
    LoginPresenterInterface {
    override fun <T> logIn(
        userName: String, password: String, onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {

        repository.logIn(userName, password, onSuccessCallback, onFailureCallback)

    }

    override fun saveTokenInShared (token : String){
        repository.saveTokenInShared(token)
    }
    override fun saveExpirationDateInShared (expirationDate : String){
        repository.saveExpirationDateInShared(expirationDate)
    }

}