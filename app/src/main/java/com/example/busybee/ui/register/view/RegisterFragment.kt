package com.example.busybee.ui.register.view

import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.SignUpResponseValue
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.databinding.FragmentRegisterBinding
import com.example.busybee.ui.login.view.LoginFragment
import com.example.busybee.ui.register.presenter.RegisterPresenter
import com.example.busybee.ui.register.presenter.RegisterPresenterInterface
import com.example.busybee.utils.LoginAndRegisterValidation
import com.example.busybee.utils.SharedPreferencesUtils
import com.example.busybee.utils.replaceFragment
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(), RegisterViewInterface {
    private val presenter: RegisterPresenterInterface by lazy {
        RegisterPresenter(
            Repository(RemoteDataSource(requireContext()),
            SharedPreferencesUtils,requireContext())
        )
    }
    private val registerValidation: LoginAndRegisterValidation by lazy {
        LoginAndRegisterValidation(requireContext())
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
            if (validateUserInputs()) {
                signUp(username, password)
            }
        }
        binding.textLogin.setOnClickListener {
            replaceFragment(loginFragment)
        }
    }

    private fun validateUserInputs(): Boolean =
        registerValidation.checkCredentialForUserName(username, binding.textFieldName)
                && registerValidation.checkCredentialForPassword(
            password,
            binding.textFieldPassword
        )
                && registerValidation.checkCredentialForConfirmPasswordPassword(
            password,
            confirmPassword,
            binding.textFieldConfirmPassword
        )

    private fun getUserInputs() {
        username = binding.textFieldName.editText?.text.toString()
        password = binding.textFieldPassword.editText?.text.toString()
        confirmPassword = binding.textFieldConfirmPassword.editText?.text.toString()
    }


    override fun signUp(userName: String, password: String) {
        presenter.signUp(
            userName,
            password,
            ::onSuccessResponse,
            ::onFailureResponse
        )
    }

    override fun onSuccessResponse(response: BaseResponse<SignUpResponseValue>) {
        activity?.runOnUiThread {
            replaceFragment(loginFragment)
            Snackbar.make(
                binding.root,
                getString(R.string.accountCreatedSuccessfully),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onFailureResponse(error: Throwable) {
        activity?.runOnUiThread {
            Snackbar.make(
                binding.root,
                getString(R.string.registerFailureMessage),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

}