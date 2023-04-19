package com.example.busybee.utils

import android.content.Context
import com.example.busybee.R

class Validator(val context: Context) {

    fun checkCredential(userName: String, password: String): Pair<Boolean, Pair<String?, String?>> {
        val usernameValid = validateUserName(userName).isEmpty()
        val passwordValid = validatePassword(password).isEmpty()
        return if (usernameValid && passwordValid) {
            Pair(true, Pair(null, null))
        } else {
            Pair(
                false, Pair(
                    if (usernameValid) null else validateUserName(userName),
                    if (passwordValid) null else validatePassword(password)
                )
            )
        }
    }

    private fun validateUserName(userName: String): String {
        return when {
            userName.isEmpty() -> context.getString(R.string.username_cannot_be_empty)
            userName.length < 4 -> context.getString(R.string.username_must_be_at_least_4)
            else -> ""
        }
    }

    private fun validatePassword(password: String): String {
        return when {
            password.isEmpty() -> context.getString(R.string.password_cannot_be_empty)
            password.length < 8 -> context.getString(R.string.password_must_be_at_least_8)
            else -> ""
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String):
            Pair<Boolean, String?> {
        return when {
            confirmPassword.isEmpty() -> Pair(
                false,
                context.getString(R.string.confirm_password_cannot_be_empty)
            )

            password != confirmPassword -> Pair(
                false,
                context.getString(R.string.passwords_do_not_match)
            )

            else -> Pair(true, null)
        }
    }

}