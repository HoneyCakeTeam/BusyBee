package com.example.busybee.ui.home.personaltask.todo.presenter

import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.ui.home.personaltask.todo.view.PersonalToDoViewInterface

class PersonalToDoPresenter(
    private val repository: Repository,
    private val personalTodoViewInterface: PersonalToDoViewInterface
) {

    fun addPersonalToDo(todo: PersonalToDo) {
        repository.addPersonalToDo(todo)
    }

    fun getLocalPersonalTodos() {
        personalTodoViewInterface.getLocalPersonalTodos(
            repository.getPersonalTasks().filter { it.status == 0 })
    }

    fun personalCreateToDo(
        title: String,
        description: String,
    ) {
        repository.createPersonalToDo(
            title,
            description,
            ::onCreatePersonalTodoSuccess,
            ::onCreatePersonalTodoFailure
        )
    }

    private fun onCreatePersonalTodoSuccess(response: BaseResponse<PersonalToDo>) {
        personalTodoViewInterface.onSuccessResponse(response)
    }

    private fun onCreatePersonalTodoFailure(error: Throwable) {
        personalTodoViewInterface.onFailureResponse(error)
    }
}