package com.example.busybee.ui.register.view

import com.example.busybee.data.models.SignUpResponse


interface RegisterViewInterface {
    fun signUp(userName: String, password: String)
    fun onSuccessResponse(response: SignUpResponse)
    fun onFailureResponse(error: Throwable)
}