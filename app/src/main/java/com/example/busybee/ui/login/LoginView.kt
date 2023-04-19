package com.example.busybee.ui.login

import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.LoginResponseValue

interface LoginView {
    fun goToHome()
    fun showErrorMsg(error: Throwable)

}

