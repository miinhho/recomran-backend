package io.miinhho.recomran.auth.token.repository

import io.miinhho.recomran.auth.token.model.RefreshToken
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "refresh_token")
class RefreshTokenEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var userId: Long? = null,

    @Column(name = "expires_at", nullable = false)
    var expiresAt: LocalDateTime,

    var hashedToken: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun toDomain(): RefreshToken {
        return RefreshToken(
            id = this.id,
            expiresAt = this.expiresAt,
            hashedToken = this.hashedToken,
            createdAt = this.createdAt
        )
    }

    companion object {
        fun fromDomain(refreshToken: RefreshToken): RefreshTokenEntity {
            return RefreshTokenEntity(
                id = refreshToken.id,
                expiresAt = refreshToken.expiresAt,
                hashedToken = refreshToken.hashedToken,
                createdAt = refreshToken.createdAt
            )
        }
    }
}