package model

data class PersonalToDoGetResponse(
    val value : List<ToDo>,
    val message : String? = null,
    val isSuccess : Boolean = false
)
