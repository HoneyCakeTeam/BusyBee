package com.example.busybee.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class TaskType : Parcelable {
    PERSONAL, TEAM
}