package com.example.busybee.domain.models

import android.os.Parcelable
import com.example.busybee.data.models.PersonalTodo
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonalTodos(
    var values: List<PersonalTodo>
) : Parcelable
