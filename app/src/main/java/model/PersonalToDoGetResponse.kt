package model

import model.Values

data class PersonalToDoGetResponse(
    val value : List<Values>,
    val message : String? = null,
    val isSuccess : Boolean = false
)
