package com.example.busybee.ui.home.personaltask.view.todo
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.PersonalToDo

interface PersonalToDoViewInterface {
    fun personalCreateToDo(title: String, description: String)
    fun onSuccessResponse(response: BaseResponse<PersonalToDo>)
    fun onFailureResponse(error: Throwable)
}
