package com.example.busybee.ui.login.view

import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.LoginResponseValue

interface LoginViewInterface {
    fun logIn(userName: String, password: String)
    fun onSuccessResponse(response: BaseResponse<LoginResponseValue>)
    fun onFailureResponse(error: Throwable)
    fun saveTokenInShared (token : String)
    fun saveExpirationDateInShared (expirationDate : String)

}
