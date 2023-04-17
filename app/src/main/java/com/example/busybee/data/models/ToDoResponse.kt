package com.example.busybee.data.models

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("value") val value: T,
    @SerializedName("message") val message: String,
    @SerializedName("isSuccess") val isSuccess: Boolean = true,
)









