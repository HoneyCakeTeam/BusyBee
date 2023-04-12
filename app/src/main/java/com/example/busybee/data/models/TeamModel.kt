package com.example.busybee.data.models

import android.os.Parcelable
import com.example.busybee.domain.models.TeamTodos
import kotlinx.parcelize.Parcelize

data class TeamCreateToDoRequest(
    val title: TeamToDo,
    val description: String?,
    val assignee: String
)

data class TeamCreateToDoResponse(
    val value: TeamToDo,
    val message: String?,
    val isSuccess: Boolean
)

data class TeamToDoListResponse(
    val value: List<TeamToDo>,
    val message: String?,
    val isSuccess: Boolean
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
    val status: Int
)

data class TeamUpdateStatusResponse(
    val value: String,
    val message: String?,
    val isSuccess: Boolean
)

fun TeamToDoListResponse.asDomainModel(): TeamTodos {
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