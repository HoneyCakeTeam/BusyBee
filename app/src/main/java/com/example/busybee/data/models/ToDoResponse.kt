package com.example.busybee.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class BaseResponse<T>(
    val value: T,
    val message: String? = null,
    val isSuccess: Boolean = true,
)
@Parcelize
data class PersonalToDo(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val status: Int? = null,
    val creationTime: String? = null,
) : Parcelable

@Parcelize
data class TeamToDo(
    val id: String,
    val title: String,
    val description: String,
    val assignee: String,
    val status: Int,
    val creationTime: String
) : Parcelable

data class LoginResponseValue(
    val token: String,
    val expireAt: String
)

data class SignUpResponseValue(
    val userId: String,
    val userName: String
)


