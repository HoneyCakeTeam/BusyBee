package com.example.busybee.ui.register.view

import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.LoginResponseValue
interface RegisterViewInterface {
    fun onRegisterSuccess(response: BaseResponse<LoginResponseValue>)
    fun onRegisterFailed(error: Throwable)
}