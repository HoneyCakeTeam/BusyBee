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

    /*private fun logIn(userName: String, password: String) {
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

    }*/

    private fun logIn(userName: String, password: String) {
        // Call the Presenter's login method with the provided username and password
        presenter.logIn<LoginResponse>(
            userName,
            password,
            onSuccessCallback = { response ->
                // Handle the successful login response here
                showLoginSuccessMessage(response)
            },
            onFailureCallback = { error ->
                // Handle the login failure here
                showLoginErrorMessage(error)
            }
        )
    }

    override fun <T> logIn(
        userName: String,
        password: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        // This method will be called by the Presenter when it receives the login response
        // You can ignore this for now
    }

    private fun showLoginSuccessMessage(response: LoginResponse) {
        // Display a success message to the user
        Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show()
    }

    private fun showLoginErrorMessage(error: Throwable) {
        // Display an error message to the user
        Toast.makeText(requireContext(), "Login failed: ${error.message}", Toast.LENGTH_SHORT)
            .show()
    }
}