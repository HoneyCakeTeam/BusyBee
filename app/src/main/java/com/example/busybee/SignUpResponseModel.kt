package com.example.busybee

data class SignUpResponseModel(
    val value: List<SignUpValues>,
    val message: String?,
    val isSuccess: Boolean
)

data class SignUpValues(
    val userId: String,
    val userName: String
)