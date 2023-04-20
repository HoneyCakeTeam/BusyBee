package com.example.busybee.utils.validator

interface Validator {
    fun checkCredential(userName: String, password: String): Pair<Boolean, Pair<String?, String?>>
    fun validateConfirmPassword(password: String, confirmPassword: String):
            Pair<Boolean, String?>
}