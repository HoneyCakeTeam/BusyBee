package com.example.busybee.ui.details

import com.example.busybee.data.models.BaseResponse

interface DetailsView {
    fun onUpdatePersonalStatusSuccess(response: BaseResponse<String>)

    fun onUpdatePersonalStatusFailed(error: Throwable)

    fun onUpdateTeamStatusSuccess(response: BaseResponse<String>)

    fun onUpdateTeamStatusFailed(error: Throwable)
}