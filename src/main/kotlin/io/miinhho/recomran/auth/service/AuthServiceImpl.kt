package io.miinhho.recomran.auth.service

import io.miinhho.recomran.auth.dto.TokenPair
import io.miinhho.recomran.auth.exception.ConflictEmailException
import io.miinhho.recomran.auth.exception.InvalidEmailException
import io.miinhho.recomran.auth.exception.InvalidPasswordException
import io.miinhho.recomran.auth.exception.InvalidTokenException
import io.miinhho.recomran.auth.token.model.RefreshToken
import io.miinhho.recomran.auth.token.repository.RefreshTokenRepository
import io.miinhho.recomran.security.jwt.HashEncoder
import io.miinhho.recomran.security.jwt.JwtService
import io.miinhho.recomran.user.model.Email
import io.miinhho.recomran.user.model.User
import io.miinhho.recomran.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.Base64

@Service
class AuthServiceImpl(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val hashEncoder: HashEncoder,
) : AuthService {
    override fun register(email: String, password: String): User {
        val email = Email(email)
        userRepository.findByEmail(email).let {
            if (it != null) throw ConflictEmailException()
        }

        return userRepository.save(
            User(
                email = email,
                hashedPassword = hashEncoder.encode(password),
            )
        )
    }

    override fun login(email: String, password: String): TokenPair {
        val email = Email(email)
        val user = userRepository.findByEmail(email)
            ?: throw InvalidEmailException()
        if (!hashEncoder.matches(password, user.password)) {
            throw InvalidPasswordException()
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