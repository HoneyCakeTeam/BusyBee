package com.example.busybee.ui.login.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.LoginResponse
import com.example.busybee.databinding.FragmentLoginBinding
import com.example.busybee.ui.login.presenter.LoginPresenter
import com.example.busybee.ui.login.presenter.LoginPresenterInterface


class LoginFragment : BaseFragment<FragmentLoginBinding>() , LoginViewInterface{

    private val presenter: LoginPresenterInterface by lazy { LoginPresenter(this , Repository()) }
    override val TAG: String = this::class.simpleName.toString()

    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)

    override fun setUp() {

        logIn("nourelden515" , "123456789")
    }

    private fun logIn(userName: String, password: String) {
        presenter.logIn<LoginResponse>(
            userName,
            password,
            {  },
            {  })
    }

    override fun <T> logIn(
        userName: String,
        password: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {

    }


}