package com.example.busybee

data class LoginResponseModel(
    val value: List<LoginResponseValue>,
    val message: String?,
    val isSuccess: Boolean
)

data class LoginResponseValue(
    val token:String,
    val expireAt:String
)