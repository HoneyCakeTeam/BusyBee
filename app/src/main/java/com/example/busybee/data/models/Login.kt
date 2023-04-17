package com.example.busybee.data.models

import com.google.gson.annotations.SerializedName

data class LoginResponseValue(
    @SerializedName("token") val token: String,
    @SerializedName("expireAt") val expireAt: String
)