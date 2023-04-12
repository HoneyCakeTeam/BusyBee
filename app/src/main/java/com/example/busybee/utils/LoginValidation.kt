package com.example.busybee.utils

import android.content.Context
import com.example.busybee.R
import com.google.android.material.textfield.TextInputLayout

class LoginValidation(val context: Context) {

    fun checkCredentialForUserName(userName: String, textInputLayout: TextInputLayout): Boolean {
        return if (userName.length < 4) {
            showError(textInputLayout, context.getString(R.string.validateUsernameMessage))
            false
        } else {
            hideError(textInputLayout)
            true
        }
    }

    fun checkCredentialForPassword(password: String, textInputLayout: TextInputLayout): Boolean {

        return if (password.length >= 8 && password.any { it != ' ' }) {
            hideError(textInputLayout)
            true
        } else {
            showError(textInputLayout, context.getString(R.string.validatePasswordMessage))
            return false
        }
    }

    private fun showError(textInputLayout: TextInputLayout, error: String) {
        textInputLayout.error = error
    }

    private fun hideError(textInputLayout: TextInputLayout) {
        textInputLayout.isErrorEnabled = false
    }

}