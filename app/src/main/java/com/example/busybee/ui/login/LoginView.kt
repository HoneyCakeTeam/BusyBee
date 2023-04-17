package com.example.busybee.ui.login

import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.LoginResponseValue

interface LoginView {
    fun onLoginSuccess(response: BaseResponse<LoginResponseValue>)
    fun onLoginFailed(error: Throwable)

}

