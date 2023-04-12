package com.example.busybee.ui.login.view

import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.LoginResponse
import com.example.busybee.databinding.FragmentLoginBinding
import com.example.busybee.ui.home.HomeFragment
import com.example.busybee.ui.login.presenter.LoginPresenter
import com.example.busybee.ui.login.presenter.LoginPresenterInterface
import com.example.busybee.ui.register.view.RegisterFragment
import com.example.busybee.utils.LoginAndRegisterValidation
import com.example.busybee.utils.replaceFragment
import com.google.android.material.snackbar.Snackbar

class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginViewInterface {
    private val presenter: LoginPresenterInterface by lazy {
        LoginPresenter(Repository(requireContext()))
    }
    private val loginAndRegisterValidation: LoginAndRegisterValidation by lazy {
        LoginAndRegisterValidation(requireContext())
    }
    private val homeFragment by lazy { HomeFragment() }
    private val registerFragment by lazy { RegisterFragment() }
    override val TAG: String = this::class.simpleName.toString()
    private var username = ""
    private var password = ""

    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)

    override fun setUp() {
        addCallBacks()
    }

    private fun addCallBacks() {
        binding.buttonLogin.setOnClickListener {
            getUserInputs()
            if (validateUserInputs()) {
                logIn(username, password)
            }
        }

        binding.textSignUp.setOnClickListener {
            replaceFragment(registerFragment)
        }
    }

    private fun validateUserInputs(): Boolean =
        loginAndRegisterValidation.checkCredentialForUserName(username, binding.editTextUsername)
                && loginAndRegisterValidation.checkCredentialForPassword(
            password,
            binding.editTextPassword
        )

    private fun getUserInputs() {
        username = binding.editTextUsername.editText?.text.toString().trim()
        password = binding.editTextPassword.editText?.text.toString()
    }

    override fun logIn(userName: String, password: String) {
        presenter.logIn(
            userName,
            password,
            ::onSuccessResponse,
            ::onFailureResponse
        )
    }

    override fun onSuccessResponse(response: LoginResponse) {
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

    override fun onFailureResponse(error: Throwable) {
        activity?.runOnUiThread {
            Snackbar.make(
                binding.root,
                getString(R.string.failure_Message),
                Snackbar.LENGTH_SHORT
            )
                .show()
        }
    }

    override fun saveTokenInShared(token: String) {
        presenter.saveTokenInShared(token)
    }

    override fun saveExpirationDateInShared(expirationDate: String) {
        presenter.saveExpirationDateInShared(expirationDate)
    }

}


