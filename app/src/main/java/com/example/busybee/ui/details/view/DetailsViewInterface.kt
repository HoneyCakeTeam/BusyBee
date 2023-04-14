package com.example.busybee.ui.details.view

import com.example.busybee.data.models.BaseResponse

interface DetailsViewInterface {
    fun onSuccessTeamResponse(response: BaseResponse<String>)
    fun updateTasksTeamStatus(idTask: String, status: Int)
    fun updateTasksPersonalStatus(idTask: String, status: Int)
    fun onSuccessPersonalResponse(response: BaseResponse<String>)
    fun onFailureResponse(error: Throwable)
}