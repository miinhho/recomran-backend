package io.miinhho.recomran.auth.token.model

import java.time.LocalDateTime

data class RefreshToken(
    val id: Long? = null,
    val userId: Long? = null,
    val expiresAt: LocalDateTime,
    val hashedToken: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)