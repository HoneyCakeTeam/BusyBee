package com.example.busybee.utils.validator

import android.content.Context
import com.example.busybee.R

class ValidatorImpl(val context: Context) : Validator {

    override fun checkCredential(
        userName: String,
        password: String
    ): Pair<Boolean, Pair<String?, String?>> {
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

    override fun validateConfirmPassword(password: String, confirmPassword: String):
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

    override fun validatePersonalTodo(
        title: String,
        description: String
    ): Pair<Boolean, Pair<String?, String?>> {
        val isTitleValid = validateTodoTitle(title).isEmpty()
        val isDescriptionValid = validateTodoDescription(description).isEmpty()
        return if (isTitleValid && isDescriptionValid) {
            Pair(true, Pair(null, null))
        } else {
            Pair(
                false, Pair(
                    if (isTitleValid) null else validateTodoTitle(title),
                    if (isDescriptionValid) null else validateTodoDescription(description)
                )
            )
        }
    }

    override fun validateTeamTodo(
        title: String,
        description: String,
        assignee: String
    ): Pair<Boolean, Triple<String?, String?, String?>> {
        val isTitleValid = validateTodoTitle(title).isEmpty()
        val isDescriptionValid = validateTodoDescription(description).isEmpty()
        val isAssigneeValid = validateTodoAssignee(assignee).isEmpty()
        return if (isTitleValid && isDescriptionValid) {
            Pair(true, Triple(null, null, null))
        } else {
            Pair(
                false, Triple(
                    if (isTitleValid) null else validateTodoTitle(title),
                    if (isDescriptionValid) null else validateTodoDescription(description),
                    if (isAssigneeValid) null else validateTodoAssignee(assignee)
                )
            )
        }
    }

    private fun validateTodoTitle(title: String): String {
        return when {
            title.isEmpty() ->
                context.getString(R.string.title_should_not_be_empty)

            title.length < 3 ->
                context.getString(R.string.title_must_be_at_least_3_characters)

            else -> ""
        }
    }

    private fun validateTodoDescription(description: String): String {
        return when {
            description.isEmpty() -> context
                .getString(R.string.description_should_not_be_empty)

            description.length < 12 -> context
                .getString(R.string.description_must_be_at_least_12_characters)

            else -> ""
        }
    }

    private fun validateTodoAssignee(assignee: String): String {
        return when {
            assignee.isEmpty() -> context
                .getString(R.string.assignee_should_not_be_empty)

            assignee.length < 3 -> context
                .getString(R.string.assignee_must_be_at_least_3_characters)

            else -> ""
        }
    }

}