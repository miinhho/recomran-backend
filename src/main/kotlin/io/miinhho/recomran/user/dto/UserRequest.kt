package io.miinhho.recomran.user.dto

data class ChangeUserPasswordRequest(
    val newPassword: String,
)

data class ChangeUserEmailRequest(
    val newEmail: String
)

data class ChangeUserNameRequest(
    val newUsername: String
)