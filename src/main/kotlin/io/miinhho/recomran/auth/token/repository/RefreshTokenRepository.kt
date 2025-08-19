package io.miinhho.recomran.auth.token.repository

import io.miinhho.recomran.auth.token.model.RefreshToken

interface RefreshTokenRepository {
    fun find(userId: Long, hashedToken: String): RefreshToken?
    fun save(refreshToken: RefreshToken): RefreshToken
    fun delete(userId: Long, hashedToken: String)
}