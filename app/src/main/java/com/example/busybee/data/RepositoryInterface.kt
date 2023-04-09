package com.example.busybee.data

interface RepositoryInterface {
    fun <T> logIn(userName: String, password: String, onSuccessCallback: (response: T) -> Unit,
                  onFailureCallback: (error: Throwable) -> Unit )
}