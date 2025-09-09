package io.miinhho.recomran.user.service

import io.miinhho.recomran.user.model.User

interface UserService {
    fun changePassword(user: User, newPassword: String)
    fun changeEmail(user: User, newEmail: String)
    fun changeUsername(user: User, newUsername: String)
}