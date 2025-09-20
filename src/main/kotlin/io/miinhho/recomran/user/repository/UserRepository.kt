package io.miinhho.recomran.user.repository

import io.miinhho.recomran.user.model.Email
import io.miinhho.recomran.user.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val userEntityRepository: UserEntityRepository
) {
    fun findById(id: Long): User? {
        return userEntityRepository.findByIdOrNull(id)?.toDomain()
    }

    fun findByEmail(email: Email): User? {
        return userEntityRepository.findByEmail(email.value)?.toDomain()
    }

    fun save(entity: User): User {
        val newEntity = UserEntity.fromDomain(entity)
        return userEntityRepository.save(newEntity).toDomain()
    }

    fun delete(id: Long) {
        userEntityRepository.deleteById(id)
    }
}

interface UserEntityRepository : CrudRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}