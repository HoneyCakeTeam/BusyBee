package com.example.busybee

import com.google.gson.annotations.SerializedName

data class CreatePersonalTodo(
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
