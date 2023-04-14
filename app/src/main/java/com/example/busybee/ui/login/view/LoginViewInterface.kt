package com.example.busybee.ui.login.view

import com.example.busybee.data.models.LoginResponse

interface LoginViewInterface {
    fun onSuccessResponse(response: LoginResponse)
    fun onFailureResponse(error: Throwable)

}

