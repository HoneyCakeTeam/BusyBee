package com.example.busybee.ui.details.view

import com.example.busybee.data.models.BaseResponse

interface DetailsViewInterface {
    fun onUpdatePersonalStatusSuccess(response: BaseResponse<String>)

    fun onUpdatePersonalStatusFailed(error: Throwable)

    fun onUpdateTeamStatusSuccess(response: BaseResponse<String>)

    fun onUpdateTeamStatusFailed(error: Throwable)
}