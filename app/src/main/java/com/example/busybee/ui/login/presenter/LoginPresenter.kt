package com.example.busybee.ui.login.presenter

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.data.source.RemoteDataSourceInterface

class LoginPresenter(
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
        repository
    }
    override fun saveExpirationDateInShared (expirationDate : String){
        repository.saveExpirationDateInShared(expirationDate)
    }

}