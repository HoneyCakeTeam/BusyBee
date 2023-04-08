package com.example.busybee.data.models

data class PersonalToDoGetResponse(
    val value : List<ToDo>,
    val message : String? = null,
    val isSuccess : Boolean = false
)
data class ToDo(
    val id: String,
    val title: String,
    val description: String,
    val status: Int,
    val creationTime: String
)
