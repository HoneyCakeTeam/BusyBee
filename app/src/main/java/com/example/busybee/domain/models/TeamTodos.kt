package com.example.busybee.domain.models

import android.os.Parcelable
import com.example.busybee.data.models.TeamToDo
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamTodos(
    var values: List<TeamToDo>
) : Parcelable