package com.example.busybee.ui.home.personaltask.inprogress

import com.example.busybee.data.models.PersonalToDo

interface PersonalInProgressView {
    fun getLocalPersonalInProgress(inProgress: List<PersonalToDo>)
}