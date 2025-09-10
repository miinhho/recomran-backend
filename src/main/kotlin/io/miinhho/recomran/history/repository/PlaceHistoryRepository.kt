package io.miinhho.recomran.history.repository

import io.miinhho.recomran.history.model.PlaceHistory
import io.miinhho.recomran.place.model.Place
import io.miinhho.recomran.place.repository.PlaceEntity
import io.miinhho.recomran.user.exception.UserNotFoundException
import io.miinhho.recomran.user.repository.UserEntity
import io.miinhho.recomran.user.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
class PlaceHistoryRepository(
    private val placeHistoryEntityRepository: PlaceHistoryEntityRepository,
    private val userRepository: UserRepository,
) {
    fun findByUserId(userId: Long, pageable: Pageable): List<PlaceHistory> {
        return placeHistoryEntityRepository.findByUserId(userId, pageable)
            .map { it.toDomain() }.toList()
    }

    fun findPlacesByUserId(userId: Long, pageable: Pageable): List<Place> {
        return placeHistoryEntityRepository.findPlacesByUserId(userId, pageable)
            .map { it.toDomain() }.toList()
    }

    fun save(entity: PlaceHistory): PlaceHistory {
        val userId = entity.userId
        val user = userRepository.findById(userId) ?: throw UserNotFoundException(userId)
        val newEntity = PlaceHistoryEntity.fromDomain(
            placeHistory = entity,
            userEntity = UserEntity.fromDomain(user)
        )

        val savedEntity = placeHistoryEntityRepository.save(newEntity)
        return savedEntity.toDomain()
    }

    fun delete(id: Long) {
        placeHistoryEntityRepository.deleteById(id)
    }
}

interface PlaceHistoryEntityRepository: JpaRepository<PlaceHistoryEntity, Long> {
    @Query("SELECT ph.place FROM PlaceHistoryEntity ph WHERE ph.user.id = :userId")
    fun findPlacesByUserId(userId: Long, pageable: Pageable): Page<PlaceEntity>

    @Query("SELECT ph FROM PlaceHistoryEntity ph WHERE ph.user.id = :userId ORDER BY ph.createdAt DESC")
    fun findByUserId(userId: Long, pageable: Pageable): Page<PlaceHistoryEntity>
}