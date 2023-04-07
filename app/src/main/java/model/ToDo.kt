package model

data class ToDo(
    val id: String,
    val title: String,
    val description: String,
    val status: Int,
    val creationTime: String
)

