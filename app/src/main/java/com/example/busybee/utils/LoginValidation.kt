package com.example.busybee.utils

import com.google.android.material.textfield.TextInputLayout

class LoginValidation {

     fun checkCredintialForUserName(userName: String , textInputLayout: TextInputLayout ) : Boolean {
        if (userName.length < 4) {
            showError(textInputLayout, "username must be four characters at least")
            return false
        }
        return true
    }

     fun checkCredintialForPassword(password: String , textInputLayout: TextInputLayout): Boolean {

        if (password.length > 8 && password.filter { it != ' ' }.length > 0) {
            return true
        }
        showError(textInputLayout, "password must be egiht characters at least")
        return false
    }

     fun showError(textInputLayout: TextInputLayout, error: String) {
        textInputLayout.error = error
    }

}