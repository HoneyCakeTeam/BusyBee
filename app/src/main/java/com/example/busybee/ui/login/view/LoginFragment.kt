package com.example.busybee.ui.login.view

import android.widget.Toast
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.LoginResponse
import com.example.busybee.databinding.FragmentLoginBinding
import com.example.busybee.ui.login.presenter.LoginPresenter
import com.example.busybee.ui.login.presenter.LoginPresenterInterface


class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginViewInterface {

    private val presenter: LoginPresenterInterface by lazy { LoginPresenter(this, Repository()) }
    override val TAG: String = this::class.simpleName.toString()

    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)

    override fun setUp() {
        logIn("nourelden515", "123456789")
    }


    override fun logIn(userName: String, password: String) {
        presenter.logIn<LoginResponse>(
            userName,
            password,
            onSuccessCallback = { response ->
                onSuccessResponse(response)
            },
            onFailureCallback = { error ->
                onFailureResponse(error)
            }
        )
    }

    override fun onSuccessResponse(response: LoginResponse) {
        // here we will update the ui
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(),
                "Login successful! ${response.isSuccess}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onFailureResponse(error: Throwable) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(),
                "Login faillll! ${error.message} ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}