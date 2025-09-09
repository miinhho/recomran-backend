package io.miinhho.recomran.auth.service

import io.miinhho.recomran.auth.dto.TokenPair
import io.miinhho.recomran.user.model.User

interface AuthService {
    fun register(email: String, password: String): User
    fun login(email: String, password: String): TokenPair
    fun refresh(refreshToken: String): TokenPair
}