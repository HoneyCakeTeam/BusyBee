package com.example.busybee.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class BaseResponse<T>(
    @SerializedName("value") val value: T,
    @SerializedName("message") val message: String,
    @SerializedName("isSuccess") val isSuccess: Boolean = true,
)

@Parcelize
data class PersonalToDo(
    @SerializedName("id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("status") var status: Int?,
    @SerializedName("creationTime") val creationTime: String? ,
) : Parcelable

@Parcelize
data class TeamToDo(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("assignee") val assignee: String,
    @SerializedName("status") var status: Int,
    @SerializedName("creationTime") val creationTime: String
) : Parcelable

data class LoginResponseValue(
    @SerializedName("token") val token: String,
    @SerializedName("expireAt") val expireAt: String
)

data class SignUpResponseValue(
    @SerializedName("userId") val userId: String,
    @SerializedName("userName") val userName: String
)


