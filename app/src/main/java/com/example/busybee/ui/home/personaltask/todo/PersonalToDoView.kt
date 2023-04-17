package com.example.busybee.ui.home.personaltask.todo

import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.PersonalToDo

interface PersonalToDoView {

    fun getLocalPersonalTodos(todos: List<PersonalToDo>)
    fun onSuccessResponse(response: BaseResponse<PersonalToDo>)
    fun onFailureResponse(error: Throwable)
}
