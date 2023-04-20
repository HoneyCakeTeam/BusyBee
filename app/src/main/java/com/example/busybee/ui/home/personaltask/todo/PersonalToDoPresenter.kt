package com.example.busybee.ui.home.personaltask.todo

import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.utils.validator.Validator

class PersonalToDoPresenter(
    private val repository: Repository,
    private val view: PersonalToDoView,
    private val validator: Validator
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

    fun validatePersonalTodo(title: String, description: String) {
        val (isValid, errorMessage) = validator.validatePersonalTodo(title, description)
        if (isValid) {
            view.hideValidationErrorThenCreatePersonalTodo(title,description)
        } else {
            view.showValidationError(
                errorMessage.first, errorMessage.second
            )
        }
    }

    private fun onCreatePersonalTodoSuccess(response: BaseResponse<PersonalToDo>) {
        view.onSuccessResponse(response)
    }

    private fun onCreatePersonalTodoFailure(error: Throwable) {
        view.onFailureResponse(error)
    }
}