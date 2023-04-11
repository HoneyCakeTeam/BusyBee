package com.example.busybee.ui.home.view.personalTask.view

import com.example.busybee.data.models.PersonalGetToDoListResponse

interface PersonalTasksViewPresenter {
    fun onSuccessResponse(response: PersonalGetToDoListResponse)
    fun onFailureResponse(error: Throwable)
}