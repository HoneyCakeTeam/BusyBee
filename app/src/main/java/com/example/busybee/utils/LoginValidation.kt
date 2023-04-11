package com.example.busybee.utils

import com.google.android.material.textfield.TextInputLayout

class LoginValidation {

     fun checkCredentialForUserName(userName: String, textInputLayout: TextInputLayout): Boolean {
         if (userName.length < 4) {
             showError(textInputLayout, "username must be four characters at least")
             return false
         }
         return true
     }

    fun checkCredentialForPassword(password: String, textInputLayout: TextInputLayout): Boolean {

        if (password.length >= 8 && password.any { it != ' ' }) {
            return true
        }
        showError(textInputLayout, "password must be egiht characters at least")
        return false
    }

    private fun showError(textInputLayout: TextInputLayout, error: String) {
        textInputLayout.error = error
    }

}