package com.example.busybee.data.models

import com.google.gson.annotations.SerializedName
data class PersonalCreateToDoRequest(
    val title: TeamToDo,
    val description: String?,
)

data class PersonalCreateToDoResponse(
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

data class PersonalGetToDoListResponse(
    val value: List<PersonalToDo>,
    val message: String? = null,
    val isSuccess: Boolean = false
)

data class PersonalToDo(
    val id: String,
    val title: String,
    val description: String,
    val status: Int,
    val creationTime: String
)

data class PersonalUpdateStatusRequest(
    val id: String,
    val status: Int
)

data class PersonalUpdateStatusResponse(
    val value: String,
    val message: String?,
    val isSuccess: Boolean
)