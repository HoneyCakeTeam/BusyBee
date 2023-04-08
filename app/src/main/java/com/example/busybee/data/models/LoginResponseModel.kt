package com.example.busybee.data.models

data class LoginResponseModel(
    val value: LoginResponseValue,
    val message: String?,
    val isSuccess: Boolean
)

data class LoginResponseValue(
    val token:String,
    val expireAt:String
)