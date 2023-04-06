package com.example.busybee

import com.google.gson.annotations.SerializedName

data class CreatePersonalTodoResponse(
    @SerializedName("value")
    val value: CreatePersonalTodo? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("isSuccess")
    val isSuccess: Boolean = true
)
