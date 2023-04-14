package com.example.busybee.data.models

import android.os.Parcelable
import com.example.busybee.domain.models.TeamTodos
import kotlinx.parcelize.Parcelize

data class BaseTeamResponse<T>(
    val value: T,
    val message: String?,
    val isSuccess: Boolean,
)

@Parcelize
data class TeamToDo(
    val id: String,
    val title: String,
    val description: String,
    val assignee: String,
    val status: Int,
    val creationTime: String
) : Parcelable

data class TeamUpdateStatusRequest(
    val id: String,
    val status: Int,
)

fun BaseTeamResponse<List<TeamToDo>>.asDomainModel(): TeamTodos {
    return TeamTodos(
        values = this.value.map {
            TeamToDo(
                id = it.id,
                title = it.title,
                description = it.description,
                assignee = it.assignee,
                status = it.status,
                creationTime = it.creationTime
            )
        }
    )
}