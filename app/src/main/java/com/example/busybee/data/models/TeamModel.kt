package com.example.busybee.data.models

data class TeamCreateToDoResponse(
    val value: Value,
    val message: String?,
    val isSuccessful: String
)

data class TeamGetToDoListResponse(
    val value: List<TeamToDo>,
    val message: String?,
    val isSuccess: Boolean
)

data class TeamToDo(
    val id: String,
    val title: String,
    val description: String,
    val assignee: String,
    val status: Int,
    val creationTime: String
)