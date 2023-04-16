package com.example.busybee.ui.register.view

import com.example.busybee.R
import com.example.busybee.ui.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.SignUpResponseValue
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.databinding.FragmentRegisterBinding
import com.example.busybee.ui.login.view.LoginFragment
import com.example.busybee.ui.register.presenter.RegisterPresenter
import com.example.busybee.utils.LoginAndRegisterValidation
import com.example.busybee.utils.SharedPreferencesUtils
import com.example.busybee.utils.replaceFragment
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(), RegisterViewInterface {
    private val presenter by lazy {
        RegisterPresenter(
            Repository(
                RemoteDataSource(requireContext()),
                SharedPreferencesUtils(
                    requireContext()
                )
            ), this
        )
    }
    private val loginFragment by lazy { LoginFragment() }
    private var username = ""
    private var password = ""
    private var confirmPassword = ""

    override val TAG: String = this::class.simpleName.toString()

    override fun getViewBinding(): FragmentRegisterBinding =
        FragmentRegisterBinding.inflate(layoutInflater)

    override fun setUp() {
        addCallBacks()
    }

    private fun addCallBacks() {
        binding.buttonSignUp.setOnClickListener {
            getUserInputs()
            validSignUp(username, password)
        }
        binding.textLogin.setOnClickListener {
            replaceFragment(loginFragment)
        }
    }

    private fun validSignUp(userName: String, password: String) {
        val validation = LoginAndRegisterValidation()
        val (isValid, errorMessage) = validation.checkCredential(userName, password)
        val (isConfirmValid, confirmPasswordErrorMessage) = validation.validateConfirmPassword(
            password,
            confirmPassword
        )
        if (isValid && isConfirmValid) {
            hideError()
            signUp(userName, password)
        } else {
            showError(errorMessage.first, errorMessage.second, confirmPasswordErrorMessage)
        }
    }

    private fun getUserInputs() {
        username = binding.textFieldName.editText?.text.toString()
        password = binding.textFieldPassword.editText?.text.toString()
        confirmPassword = binding.textFieldConfirmPassword.editText?.text.toString()
    }

    private fun showError(
        usernameErrorMessage: String?,
        passwordErrorMessage: String?,
        confirmPasswordErrorMessage: String?
    ) {
        binding.textFieldName.error = usernameErrorMessage
        binding.textFieldPassword.error = passwordErrorMessage
        binding.textFieldConfirmPassword.error = confirmPasswordErrorMessage
    }


    private fun hideError() {
        binding.textFieldName.isErrorEnabled = false
        binding.textFieldPassword.isErrorEnabled = false
        binding.textFieldConfirmPassword.isErrorEnabled = false
    }

    private fun signUp(userName: String, password: String) {
        presenter.signUp(
            userName,
            password
        )
    }

    override fun onRegisterSuccess(response: BaseResponse<SignUpResponseValue>) {
        activity?.runOnUiThread {
            replaceFragment(loginFragment)
            Snackbar.make(
                binding.root,
                getString(R.string.accountCreatedSuccessfully),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onRegisterFailed(error: Throwable) {
        activity?.runOnUiThread {
            Snackbar.make(
                binding.root,
                getString(R.string.registerFailureMessage),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

}