package com.example.busybee.ui.login.view

import com.example.busybee.R
import com.example.busybee.data.RepositoryImp
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.LoginResponseValue
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.databinding.FragmentLoginBinding
import com.example.busybee.ui.base.BaseFragment
import com.example.busybee.ui.home.HomeFragment
import com.example.busybee.ui.login.presenter.LoginPresenter
import com.example.busybee.ui.register.view.RegisterFragment
import com.example.busybee.utils.*
import com.google.android.material.snackbar.Snackbar

class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginViewInterface {
    private val presenter by lazy {
        LoginPresenter(
            RepositoryImp(
                RemoteDataSource(requireContext()),
                SharedPreferencesUtils(requireContext())
            ), this
        )
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
            validLogin(userName,password)
        }

        binding.textSignUp.setOnClickListener {
            replaceFragment(registerFragment)
        }
    }


    private fun validLogin(userName: String,password: String){
        val validation = LoginAndRegisterValidation()
        val (isValid, errorMessage) = validation.checkCredential(userName, password)
        if (isValid){
            hideError()
            login(userName, password)
        }
        else{
            showError(errorMessage.first, errorMessage.second)
        }
    }
    private fun showError(usernameErrorMessage: String?, passwordErrorMessage: String?) {
        binding.editTextUsername.error = usernameErrorMessage
        binding.editTextPassword.error = passwordErrorMessage
    }


    private fun hideError() {
            binding.editTextPassword.isErrorEnabled = false
            binding.editTextUsername.isErrorEnabled = false
    }
    private fun getUserInputs() {
        userName = binding.editTextUsername.editText?.text.toString().trim()
        password = binding.editTextPassword.editText?.text.toString()
    }

    private fun login(userName: String, password: String) {

        if (isOnline(requireContext())) {
            presenter.logIn(
                userName,
                password
            )
        }else{
            Snackbar.make(
                binding.root,
               getString(R.string.no_internt),
                Snackbar.LENGTH_SHORT
            )
                .show()
        }

    }

    override fun onLoginSuccess(response: BaseResponse<LoginResponseValue>) {
        activity?.runOnUiThread {
            if (response.isSuccess) {
                setToken(response.value.token)
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

    private fun setToken(token: String) {
        presenter.saveTokenInShared(token)
    }

}


