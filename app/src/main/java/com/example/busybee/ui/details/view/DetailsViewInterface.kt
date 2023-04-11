package com.example.busybee.ui.details.view

import com.example.busybee.data.models.PersonalUpdateStatusResponse
import com.example.busybee.data.models.TeamUpdateStatusResponse

interface DetailsViewInterface {
    fun onSuccessTeamResponse(response: TeamUpdateStatusResponse)
    fun updateTasksTeamStatus(idTask: String, status: Int)
    fun updateTasksPersonalStatus(idTask: String, status: Int)
    fun onSuccessPersonalResponse(response: PersonalUpdateStatusResponse)
    fun onFailureResponse(error: Throwable)
}