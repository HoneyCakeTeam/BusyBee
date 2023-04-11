package com.example.busybee.ui.login.view

import android.widget.Toast
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.LoginResponse
import com.example.busybee.databinding.FragmentLoginBinding
import com.example.busybee.ui.login.presenter.LoginPresenter
import com.example.busybee.ui.login.presenter.LoginPresenterInterface
import com.example.busybee.utils.LoginValidation
import com.example.busybee.utils.SharedPreferencesUtils

class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginViewInterface {

    private val presenter: LoginPresenterInterface by lazy {
        LoginPresenter( Repository(requireContext()) )
    }
    private val loginValidation : LoginValidation = LoginValidation()
    override val TAG: String = this::class.simpleName.toString()
    private var username = ""
    private var password = ""
    private var token = ""
    private var expirationDate = ""
    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)

    override fun setUp() {

        //  logIn("nourelden515", "123456789")

        binding.buttonLogin.setOnClickListener {
            username = binding.editTextUsername.editText?.text.toString().trim()
            password = binding.editTextPassword.editText?.text.toString()

            if (loginValidation.checkCredentialForUserName(username , binding.editTextUsername)
                && loginValidation.checkCredentialForPassword(password , binding.editTextPassword) )
                logIn(username, password)
        }

        binding.textSignUp.setOnClickListener {
            // go to sign up fragment
        }

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
            token = response.value.token
            expirationDate = response.value.expireAt
            // here we will move to home fragment
            if (response.isSuccess) {
                Toast.makeText(
                    requireContext(),
                    "Loged in successfully", Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "${response.message}", Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    override fun onFailureResponse(error: Throwable) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(), "cant log in", Toast.LENGTH_SHORT
            ).show()
        }
    }
    override fun saveTokenInShared(token: String) {
        presenter.saveTokenInShared(token)
    }

    override fun saveExpirationDateInShared(expirationDate: String) {
        presenter.saveExpirationDateInShared(expirationDate)
    }

}


