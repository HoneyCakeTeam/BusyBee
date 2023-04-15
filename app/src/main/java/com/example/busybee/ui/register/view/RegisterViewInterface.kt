package com.example.busybee.ui.register.view

import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.SignUpResponseValue

interface RegisterViewInterface {
    fun onRegisterSuccess(response: BaseResponse<SignUpResponseValue>)
    fun onRegisterFailed(error: Throwable)
}