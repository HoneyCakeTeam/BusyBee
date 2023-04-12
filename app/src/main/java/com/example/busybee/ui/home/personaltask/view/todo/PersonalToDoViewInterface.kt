package com.example.busybee.ui.home.personaltask.view.todo
import com.example.busybee.data.models.PersonalCreateToDoResponse

interface PersonalToDoViewInterface {
    fun personalCreateToDo(title: String, description: String)
    fun onSuccessResponse(response: PersonalCreateToDoResponse)
    fun onFailureResponse(error: Throwable)
}