package com.example.busybee.ui.home.personaltask.done.view

import com.example.busybee.data.models.PersonalToDo

interface PersonalDoneViewInterface {
    fun getLocalPersonalDones(dones: List<PersonalToDo>)
}