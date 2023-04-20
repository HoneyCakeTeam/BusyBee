package com.example.busybee.ui.login

import android.view.View
import androidx.core.content.ContextCompat
import com.example.busybee.R
import com.example.busybee.data.RepositoryImp
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.data.source.RemoteDataSourceImp
import com.example.busybee.databinding.FragmentLoginBinding
import com.example.busybee.ui.base.BaseFragment
import com.example.busybee.ui.home.HomeFragment
import com.example.busybee.ui.register.RegisterFragment
import com.example.busybee.utils.onClickBackFromNavigation
import com.example.busybee.utils.replaceFragment
import com.example.busybee.utils.setStatusBarBackgroundColor
import com.example.busybee.utils.sharedpreference.SharedPreferencesInterface
import com.example.busybee.utils.sharedpreference.SharedPreferencesUtils
import com.example.busybee.utils.validator.Validator
import com.example.busybee.utils.validator.ValidatorImpl
import com.google.android.material.snackbar.Snackbar

class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginView {
    private val sharedPreferences: SharedPreferencesInterface by lazy {
        SharedPreferencesUtils(
            requireContext()
        )
    }
    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSourceImp(requireContext())
    }
    private val validator: Validator by lazy { ValidatorImpl(requireContext()) }
    private val presenter by lazy {
        LoginPresenter(
            RepositoryImp(
                remoteDataSource,
                sharedPreferences
            ), this,
            validator
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

        setStatusBarBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary_300))
        addCallBacks()
        onClickBackFromNavigation()
    }

    private fun addCallBacks() {
        binding.buttonLogin.setOnClickListener {
            getUserInputs()
            presenter.validateLoginData(userName, password)
        }

        binding.textSignUp.setOnClickListener {
            replaceFragment(registerFragment)
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
        binding.lottieLoading.visibility = View.VISIBLE
        binding.scrollView.visibility = View.GONE
        // if (isOnline(requireContext())) {
        presenter.logIn(
            userName,
            password
        )
//        }else{
//            Snackbar.make(
//                binding.root,
//               getString(R.string.no_internt),
//                Snackbar.LENGTH_SHORT
//            )
//                .show()
//        }

    }

    override fun goToHome() {
        activity?.runOnUiThread {
            binding.lottieLoading.visibility = View.VISIBLE
            binding.scrollView.visibility = View.GONE
            replaceFragment(homeFragment)
            Snackbar.make(
                binding.root,
                getString(R.string.success_message),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun showErrorMsg(error: Throwable) {
        activity?.runOnUiThread {
            Snackbar.make(
                binding.root,
                getString(R.string.failure_Message),
                Snackbar.LENGTH_SHORT
            )
                .show()
        }
    }

    override fun showValidationError(usernameErrorMessage: String?, passwordErrorMessage: String?) {
        showError(usernameErrorMessage, passwordErrorMessage)
    }

    override fun hideValidationErrorThenLogin(username: String, password: String) {
        hideError()
        login(username, password)
    }
}


