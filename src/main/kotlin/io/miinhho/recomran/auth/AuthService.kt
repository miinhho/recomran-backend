package io.miinhho.recomran.auth

import io.miinhho.recomran.auth.token.RefreshToken
import io.miinhho.recomran.auth.token.RefreshTokenRepository
import io.miinhho.recomran.security.jwt.HashEncoder
import io.miinhho.recomran.security.jwt.JwtService
import io.miinhho.recomran.user.User
import io.miinhho.recomran.user.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
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
    data class TokenPair(
        val accessToken: String,
        val refreshToken: String,
    )

    fun register(email: String, password: String): User {
        userRepository.findByEmail(email.trim()) ?: throw ResponseStatusException(
            HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."
        )
        return userRepository.save(
            User(
                email = email,
                password = hashEncoder.encode(password),
            )
        )
    }

    fun login(email: String, password: String): TokenPair {
        val user = userRepository.findByEmail(email)
            ?: throw BadCredentialsException("유효하지 않은 이메일입니다.")
        if (!hashEncoder.matches(password, user.password)) {
            throw BadCredentialsException("유효하지 않은 비밀번호입니다.")
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
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.")
        }
        val userId = jwtService.getUserIdFromToken(refreshToken)
        val user = userRepository.findById(userId).orElseThrow {
            ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다")
        }

        val hashed = hashToken(refreshToken)
        refreshTokenRepository.findByUserIdAndHashedToken(user.id!!, hashed)
            ?: throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "토큰을 찾을 수 없습니다."
            )

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
        val expiryMs = jwtService.refreshTokenValidifyMs
        val expiresAt = LocalDateTime.now().plusSeconds(expiryMs / 1000)

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