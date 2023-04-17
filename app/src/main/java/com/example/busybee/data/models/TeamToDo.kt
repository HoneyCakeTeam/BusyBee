package com.example.busybee.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamToDo(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("assignee") val assignee: String,
    @SerializedName("status") var status: Int,
    @SerializedName("creationTime") val creationTime: String
) : Parcelable