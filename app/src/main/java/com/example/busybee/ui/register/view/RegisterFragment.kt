package com.example.busybee.ui.register.view

import android.widget.Toast
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.SignUpResponse
import com.example.busybee.databinding.FragmentRegisterBinding
import com.example.busybee.ui.register.presenter.RegisterPresenter
import com.example.busybee.ui.register.presenter.RegisterPresenterInterface

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(), RegisterViewInterface {

    private val presenter: RegisterPresenterInterface by lazy {
        RegisterPresenter(Repository(requireContext()))
    }
    override val TAG: String = this::class.simpleName.toString()

    override fun getViewBinding(): FragmentRegisterBinding =
        FragmentRegisterBinding.inflate(layoutInflater)

    override fun setUp() {
        binding.buttonSignUp.setOnClickListener {
            val userName = binding.textFieldName.editText?.text.toString()
            val password = binding.textFieldPassword.editText?.text.toString()
            val confirmPassword = binding.textFieldConfirmPassword.editText?.text.toString()
            signUp(userName, password)
        }
    }


    override fun signUp(userName: String, password: String) {
        presenter.signUp<SignUpResponse>(
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

    override fun onSuccessResponse(response: SignUpResponse) {
        // here we will update the ui
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(),
                "sign up successful",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onFailureResponse(error: Throwable) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(),
                "${error.message} ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}