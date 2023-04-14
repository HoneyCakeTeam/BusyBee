package com.example.busybee.ui.login.view

import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.LoginResponseValue
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.databinding.FragmentLoginBinding
import com.example.busybee.ui.home.HomeFragment
import com.example.busybee.ui.login.presenter.LoginPresenter
import com.example.busybee.ui.register.view.RegisterFragment
import com.example.busybee.utils.LoginAndRegisterValidation
import com.example.busybee.utils.SharedPreferencesUtils
import com.example.busybee.utils.onClickBackFromNavigation
import com.example.busybee.utils.replaceFragment
import com.google.android.material.snackbar.Snackbar

class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginViewInterface {
    private val presenter by lazy {
        LoginPresenter(
            Repository(
                RemoteDataSource(requireContext()),
                SharedPreferencesUtils, requireContext()
            ), this
        )
    }
    private val loginAndRegisterValidation: LoginAndRegisterValidation by lazy {
        LoginAndRegisterValidation(requireContext())
    }
    private val homeFragment by lazy { HomeFragment() }
    private val registerFragment by lazy { RegisterFragment() }
    override val TAG: String = this::class.simpleName.toString()
    private var userName = ""
    private var password = ""

    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)

    override fun setUp() {
        addCallBacks()
        onClickBackFromNavigation()
    }

    private fun addCallBacks() {
        binding.buttonLogin.setOnClickListener {
            getUserInputs()
            if (validateUserInputs()) {

                login(userName, password)
            }
        }

        binding.textSignUp.setOnClickListener {
            replaceFragment(registerFragment)
        }
    }


    private fun validateUserInputs(): Boolean =
        loginAndRegisterValidation.checkCredentialForUserName(userName, binding.editTextUsername)
                && loginAndRegisterValidation.checkCredentialForPassword(
            password,
            binding.editTextPassword
        )

    private fun getUserInputs() {
        userName = binding.editTextUsername.editText?.text.toString().trim()
        password = binding.editTextPassword.editText?.text.toString()
    }

    private fun login(userName: String, password: String) {
        presenter.logIn<BaseResponse<LoginResponseValue>>(
            userName,
            password
        )
    }

    override fun onLoginSuccess(response: BaseResponse<LoginResponseValue>) {
        activity?.runOnUiThread {
            if (response.isSuccess) {
                saveTokenInShared(response.value.token)
                saveExpirationDateInShared(response.value.expireAt)
                replaceFragment(homeFragment)
                Snackbar.make(
                    binding.root,
                    getString(R.string.success_message),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.unRegisterd),
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }

        }
    }

    override fun onLoginFailed(error: Throwable) {
        activity?.runOnUiThread {
            Snackbar.make(
                binding.root,
                getString(R.string.failure_Message),
                Snackbar.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun saveTokenInShared(token: String) {
        presenter.saveTokenInShared(token)
    }

    private fun saveExpirationDateInShared(expirationDate: String) {
        presenter.saveExpirationDateInShared(expirationDate)
    }

}


