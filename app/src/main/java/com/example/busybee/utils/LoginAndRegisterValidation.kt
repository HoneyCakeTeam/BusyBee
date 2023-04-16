package com.example.busybee.utils

class LoginAndRegisterValidation {

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
            userName.isEmpty() -> "Username cannot be empty."
            userName.length < 4 -> "Username must be at least 4."
            else -> ""
        }
    }

    private fun validatePassword(password: String): String {
        return when {
            password.isEmpty() -> "Password cannot be empty."
            password.length < 8 -> "Password must be at least 8."
            else -> ""
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): Pair<Boolean, String?> {
        return when {
            password.isEmpty() -> Pair(false, "Confirm password cannot be empty.")
            confirmPassword != null && password != confirmPassword -> Pair(
                false,
                "Passwords do not match."
            )

            else -> Pair(true, null)
        }
    }

}