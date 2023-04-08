package com.example.busybee.data.models

import com.google.gson.annotations.SerializedName

data class PersonalTodoCreationResponse(
    @SerializedName("value")
    val value: PersonalTodo? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("isSuccess")
    val isSuccess: Boolean = true
)

data class PersonalTodo(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("creationTime")
    val creationTime: String? = null,
)

