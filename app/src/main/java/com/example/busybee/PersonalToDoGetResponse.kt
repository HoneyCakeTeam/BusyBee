package com.example.busybee

data class PersonalToDoGetResponse(
    val value : List<Values>,
    val message : String? = null,
    val isSuccess : Boolean = false
)
