package com.example.busybee.ui.home.personaltask.view.todo
import com.example.busybee.data.models.PersonalCreateToDoResponse
import com.example.busybee.data.models.PersonalToDoListResponse

interface PersonalToDoViewInterface {
    fun personalCreateToDo(title: String, description: String)
    fun onSuccessResponse(response: PersonalCreateToDoResponse)
    fun onFailureResponse(error: Throwable)
}
