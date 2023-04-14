package com.example.busybee.ui.home.personaltask.view.todo
import com.example.busybee.data.models.BasePersonalResponse
import com.example.busybee.data.models.BaseTeamResponse
import com.example.busybee.data.models.PersonalTodo

interface PersonalToDoViewInterface {
    fun personalCreateToDo(title: String, description: String)
    fun onSuccessResponse(response: BasePersonalResponse<PersonalTodo>)
    fun onFailureResponse(error: Throwable)
}
