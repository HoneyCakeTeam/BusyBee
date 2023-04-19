package com.example.busybee.ui.home.personaltask.todo

import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.PersonalToDo

class PersonalToDoPresenter(
    private val repository: Repository,
    private val view: PersonalToDoView
) {

    fun addPersonalToDo(todo: PersonalToDo) {
        repository.addPersonalToDo(todo)
    }

    fun getLocalPersonalTodos() {
        view.getLocalPersonalTodos(
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
        view.onSuccessResponse(response)
    }

    private fun onCreatePersonalTodoFailure(error: Throwable) {
        view.onFailureResponse(error)
    }
}