package com.example.busybee.ui.details.view

import com.example.busybee.data.models.LoginResponse
import com.example.busybee.data.models.PersonalUpdateStatusResponse

interface DetailsViewInterface {
    fun updateTasksPersonalStatus (idTask :String , status :Int)
    fun onSuccessPersonalResponse(response: PersonalUpdateStatusResponse)
    fun onFailureResponse(error: Throwable)
}