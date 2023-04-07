package com.example.busybee

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Aziza Helmy on 4/6/2023.
 */

data class TeamResponseModel(
    val value: List<Value>,
    val message: String?,
    val isSuccess: Boolean
)

data class Value(
    val id: String,
    val title: String?,
    val description: String?,
    val assignee: String?,
    val status: Int,
    val creationTime: String
)