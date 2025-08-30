package io.miinhho.recomran.auth.token.repository.impl

import io.miinhho.recomran.auth.exception.InvalidTokenException
import io.miinhho.recomran.auth.token.model.RefreshToken
import io.miinhho.recomran.auth.token.repository.RefreshTokenRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
class JpaRefreshTokenRepository(
    private val refreshTokenEntityRepository: RefreshTokenEntityRepository
) : RefreshTokenRepository {
    override fun find(userId: Long, hashedToken: String): RefreshToken? {
        val refreshTokenEntity = refreshTokenEntityRepository
            .findByUserIdAndHashedToken(userId, hashedToken) ?: throw InvalidTokenException()
        return refreshTokenEntity.toDomain()
    }

    @Transactional
    override fun save(refreshToken: RefreshToken): RefreshToken {
        val newEntity = RefreshTokenEntity.fromDomain(refreshToken)
        val savedEntity = refreshTokenEntityRepository.save(newEntity)
        return savedEntity.toDomain()
    }

    @Transactional
    override fun delete(userId: Long, hashedToken: String) {
        refreshTokenEntityRepository.deleteByUserIdAndHashedToken(userId, hashedToken)
    }
}

interface RefreshTokenEntityRepository: CrudRepository<RefreshTokenEntity, Long> {
    fun findByUserIdAndHashedToken(userId: Long, hashedToken: String): RefreshTokenEntity?
    fun deleteByUserIdAndHashedToken(userId: Long, hashedToken: String)
}