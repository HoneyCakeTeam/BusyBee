package com.example.busybee.ui.register.view

import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.SignUpResponseValue


interface RegisterViewInterface {
    fun signUp(userName: String, password: String)
    fun onSuccessResponse(response: BaseResponse<SignUpResponseValue>)
    fun onFailureResponse(error: Throwable)
}