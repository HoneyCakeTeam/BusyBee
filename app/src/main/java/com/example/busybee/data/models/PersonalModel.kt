package com.example.busybee.data.models

import android.os.Parcelable
import com.example.busybee.domain.models.PersonalTodos
import kotlinx.parcelize.Parcelize

data class PersonalCreateToDoRequest(
    val title: TeamToDo,
    val description: String?,
)
data class BasePersonalResponse<T>(
    val value: T,
    val message: String? = null,
    val isSuccess: Boolean = true,
)
@Parcelize
data class PersonalTodo(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val status: Int? = null,
    val creationTime: String? = null,
) : Parcelable

data class PersonalUpdateStatusRequest(
    val id: String,
    val status: Int
)
fun BasePersonalResponse<List<PersonalTodo>>.asDomainModel(): PersonalTodos {
    return PersonalTodos(
        values = this.value.map {
            PersonalTodo(
                id = it.id,
                title = it.title,
                description = it.description,
                status = it.status,
                creationTime = it.creationTime
            )
        }
    )
}