package com.example.busybee.ui.register

import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.SignUpResponseValue

interface RegisterView {
    fun onRegisterSuccess(response: BaseResponse<SignUpResponseValue>)
    fun onRegisterFailed(error: Throwable)
}