package io.miinhho.recomran.auth.token

import io.miinhho.recomran.common.entity.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "refresh_token")
data class RefreshToken(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var userId: Long? = null,
    var expiresAt: LocalDateTime,
    var hashedToken: String,
): BaseTimeEntity()
