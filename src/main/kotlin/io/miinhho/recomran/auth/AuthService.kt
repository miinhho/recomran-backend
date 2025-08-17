package io.miinhho.recomran.auth

import io.miinhho.recomran.auth.exception.ConflictEmailException
import io.miinhho.recomran.auth.exception.InvalidEmailException
import io.miinhho.recomran.auth.exception.InvalidPasswordException
import io.miinhho.recomran.auth.exception.InvalidTokenException
import io.miinhho.recomran.auth.token.RefreshToken
import io.miinhho.recomran.auth.token.RefreshTokenRepository
import io.miinhho.recomran.auth.token.TokenUtil
import io.miinhho.recomran.security.jwt.HashEncoder
import io.miinhho.recomran.security.jwt.JwtService
import io.miinhho.recomran.user.User
import io.miinhho.recomran.user.UserRepository
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.Base64

@Service
class AuthService(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val hashEncoder: HashEncoder,
) {

    fun register(email: String, password: String): User {
        userRepository.findByEmail(email.trim()) ?: throw ConflictEmailException()
        return userRepository.save(
            User(
                email = email,
                password = hashEncoder.encode(password),
            )
        )
    }

    fun login(email: String, password: String): TokenPair {
        val user = userRepository.findByEmail(email)
            ?: throw InvalidEmailException()
        if (!hashEncoder.matches(password, user.password)) {
            throw InvalidPasswordException()
        }

        val userIdToHex = user.id!!.toHexString()
        val newAccessToken = jwtService.generateAccessToken(userIdToHex)
        val newRefreshToken = jwtService.generateRefreshToken(userIdToHex)

        storeRefreshToken(user.id!!, newRefreshToken)

        return TokenPair(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
        )
    }

    @Transactional
    fun refresh(refreshToken: String): TokenPair {
        if (!jwtService.validateRefreshToken(refreshToken)) {
            throw InvalidTokenException()
        }
        val userId = jwtService.getUserIdFromToken(refreshToken)
        val user = userRepository.findById(userId).orElseThrow {
            throw InvalidTokenException()
        }

        val hashed = hashToken(refreshToken)
        refreshTokenRepository.findByUserIdAndHashedToken(user.id!!, hashed)
            ?: throw InvalidTokenException()

        refreshTokenRepository.deleteByUserIdAndHashedToken(user.id!!, hashed)

        val newAccessToken = jwtService.generateAccessToken(userId.toHexString())
        val newRefreshToken = jwtService.generateRefreshToken(userId.toHexString())

        storeRefreshToken(user.id!!, newRefreshToken)

        return TokenPair(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

    private fun storeRefreshToken(userId: Long, rawRefreshToken: String) {
        val hashedToken = hashToken(rawRefreshToken)
        val expiresAt = LocalDateTime.now().plusSeconds(
            JwtService.REFRESH_TOKEN_VALID_MS / 1000)

        refreshTokenRepository.save(
            RefreshToken(
                userId = userId,
                expiresAt = expiresAt,
                hashedToken = hashedToken
            )
        )
    }

    private fun hashToken(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(token.encodeToByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }
}

data class TokenPair(
    val accessToken: String,
    val refreshToken: String,
) {
    fun toRefreshCookie(): ResponseCookie = TokenUtil.getRefreshCookie(this.refreshToken)
}