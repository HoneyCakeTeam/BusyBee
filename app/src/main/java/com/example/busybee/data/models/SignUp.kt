package com.example.busybee.data.models

import com.google.gson.annotations.SerializedName

data class SignUpResponseValue(
    @SerializedName("userId") val userId: String="",
    @SerializedName("username") val userName: String=""
)
