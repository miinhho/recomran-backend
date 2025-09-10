package io.miinhho.recomran.auth.service

import io.miinhho.recomran.auth.dto.TokenPair
import io.miinhho.recomran.auth.exception.ConflictEmailException
import io.miinhho.recomran.auth.exception.IllegalEmailException
import io.miinhho.recomran.auth.exception.IllegalPasswordException
import io.miinhho.recomran.auth.exception.InvalidTokenException
import io.miinhho.recomran.auth.token.model.RefreshToken
import io.miinhho.recomran.auth.token.repository.RefreshTokenRepository
import io.miinhho.recomran.security.jwt.HashEncoder
import io.miinhho.recomran.security.jwt.JwtService
import io.miinhho.recomran.user.model.Email
import io.miinhho.recomran.user.model.Password
import io.miinhho.recomran.user.model.User
import io.miinhho.recomran.user.repository.UserRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.Base64

@Primary
@Service
class JwtAuthService(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val hashEncoder: HashEncoder,
) : AuthService {
    @Transactional
    override fun register(email: String, password: String): User {
        val email = Email.from(email)
        userRepository.findByEmail(email).let {
            if (it != null) throw ConflictEmailException()
        }

        val hashedPassword = Password.from(hashEncoder.encode(password))
        val user = User(
            email = email,
            hashedPassword = hashedPassword
        )
        return userRepository.save(user)
    }

    @Transactional
    override fun login(email: String, password: String): TokenPair {
        val email = Email.from(email)
        val user = userRepository.findByEmail(email) ?: throw IllegalEmailException()
        if (!hashEncoder.matches(password, user.password)) {
            throw IllegalPasswordException()
        }

        val userIdToHex = user.id!!.toHexString()
        val newAccessToken = jwtService.generateAccessToken(userIdToHex)
        val newRefreshToken = jwtService.generateRefreshToken(userIdToHex)

        storeRefreshToken(user.id, newRefreshToken)

        return TokenPair(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
        )
    }

    @Transactional
    override fun refresh(refreshToken: String): TokenPair {
        if (!jwtService.validateRefreshToken(refreshToken)) {
            throw InvalidTokenException()
        }
        val userId = jwtService.getUserIdFromToken(refreshToken)
        val user = userRepository.findById(userId) ?: throw InvalidTokenException()

        val hashed = hashToken(refreshToken)
        refreshTokenRepository.find(user.id!!, hashed)
            ?: throw InvalidTokenException()

        refreshTokenRepository.delete(user.id, hashed)

        val newAccessToken = jwtService.generateAccessToken(userId.toHexString())
        val newRefreshToken = jwtService.generateRefreshToken(userId.toHexString())

        storeRefreshToken(user.id, newRefreshToken)

        return TokenPair(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

    @Transactional
    private fun storeRefreshToken(userId: Long, rawRefreshToken: String) {
        val hashedToken = hashToken(rawRefreshToken)
        val expiresAt = LocalDateTime.now().plusSeconds(
            JwtService.REFRESH_TOKEN_VALID_MS / 1000)

        val refreshToken = RefreshToken(
            userId = userId,
            expiresAt = expiresAt,
            hashedToken = hashedToken
        )
        refreshTokenRepository.save(refreshToken)
    }

    private fun hashToken(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(token.encodeToByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }
}