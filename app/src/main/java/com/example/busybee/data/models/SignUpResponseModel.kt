package com.example.busybee.data.models

data class SignUpResponseModel(
    val value: SignUpValues,
    val message: String?,
    val isSuccess: Boolean
)

data class SignUpValues(
    val userId: String,
    val userName: String
)