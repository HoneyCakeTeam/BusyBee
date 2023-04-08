package com.example.busybee.data.models

//region Login
data class LoginRequest(
    val username: String,
    val password: String,
    val teamId: String
)

data class LoginResponse(
    val value: LoginResponseValue,
    val message: String?,
    val isSuccess: Boolean
)

data class LoginResponseValue(
    val token: String,
    val expireAt: String
)
//endregion

//region Register
data class SignUpResponse(
    val value: SignUpValue,
    val message: String?,
    val isSuccess: Boolean
)

data class SignUpValue(
    val userId: String,
    val userName: String
)
//endregion