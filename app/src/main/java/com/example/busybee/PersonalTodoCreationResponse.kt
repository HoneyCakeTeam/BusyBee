package com.example.busybee

import com.google.gson.annotations.SerializedName

data class PersonalTodoCreationResponse(
    @SerializedName("value")
    val value: PersonalCreationTodo? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("isSuccess")
    val isSuccess: Boolean = true
)
