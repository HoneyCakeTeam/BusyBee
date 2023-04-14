package com.example.busybee.ui.login.view

import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.LoginResponseValue

interface LoginViewInterface {
    fun onLoginSuccess(response: BaseResponse<LoginResponseValue>)
    fun onLoginFailed(error: Throwable)

}

