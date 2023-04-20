package com.example.busybee.utils.validator

interface Validator {
    fun checkCredential(
        userName: String,
        password: String
    ): Pair<Boolean, Pair<String?, String?>>

    fun validateConfirmPassword(
        password: String,
        confirmPassword: String
    ): Pair<Boolean, String?>

    fun validatePersonalTodo(
        title: String,
        description: String
    ): Pair<Boolean, Pair<String?, String?>>

    fun validateTeamTodo(
        title: String,
        description: String,
        assignee: String
    ): Pair<Boolean, Triple<String?, String?, String?>>
}