package com.example.busybee.ui.details.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.models.PersonalUpdateStatusResponse
import com.example.busybee.databinding.FragmentDetailsBinding
import com.example.busybee.databinding.FragmentLoginBinding
import com.example.busybee.ui.login.view.LoginViewInterface


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(), DetailsViewInterface {
    override val TAG: String
        get() = TODO("Not yet implemented")

    override fun getViewBinding(): FragmentDetailsBinding {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun setUp() {
        TODO("Not yet implemented")
    }

    override fun updateTasksPersonalStatus(id: String, status: Int) {
        TODO("Not yet implemented")
    }

    override fun onSuccessPersonalResponse(response: PersonalUpdateStatusResponse) {
        TODO("Not yet implemented")
    }


    override fun onFailureResponse(error: Throwable) {
        TODO("Not yet implemented")
    }


}