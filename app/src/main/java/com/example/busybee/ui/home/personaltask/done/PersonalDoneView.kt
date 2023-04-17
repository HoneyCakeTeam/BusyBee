package com.example.busybee.ui.home.personaltask.done

import com.example.busybee.data.models.PersonalToDo

interface PersonalDoneView {
    fun getLocalPersonalDones(dones: List<PersonalToDo>)
}