package io.miinhho.recomran.history.repository.impl

import io.miinhho.recomran.history.repository.PlaceHistoryRepository
import io.miinhho.recomran.history.model.PlaceHistory
import io.miinhho.recomran.place.model.Place
import io.miinhho.recomran.place.repository.impl.PlaceEntity
import io.miinhho.recomran.user.exception.UserNotFoundException
import io.miinhho.recomran.user.repository.impl.UserEntityRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
class JpaPlaceHistoryRepository(
    private val placeHistoryEntityRepository: PlaceHistoryEntityRepository,
    private val userEntityRepository: UserEntityRepository,
) : PlaceHistoryRepository {
    override fun findByUserId(userId: Long, pageable: Pageable): List<PlaceHistory> {
        return placeHistoryEntityRepository.findByUserId(userId, pageable)
            .map { it.toDomain() }.toList()
    }

    override fun findPlacesByUserId(userId: Long, pageable: Pageable): List<Place> {
        return placeHistoryEntityRepository.findPlacesByUserId(userId, pageable)
            .map { it.toDomain() }.toList()
    }

    override fun save(entity: PlaceHistory): PlaceHistory {
        val userId = entity.user.id
            ?: throw NullPointerException("PlaceHistory 의 user id 가 유효하지 않습니다: ${entity.id}")
        val userEntity = userEntityRepository.findById(userId)
            .orElseThrow { UserNotFoundException(userId) }

        val newEntity = PlaceHistoryEntity.fromDomain(entity, userEntity)
        val savedEntity = placeHistoryEntityRepository.save(newEntity)
        return savedEntity.toDomain()

    }

    override fun delete(id: Long) {
        placeHistoryEntityRepository.deleteById(id)
    }
}

interface PlaceHistoryEntityRepository: JpaRepository<PlaceHistoryEntity, Long> {
    @Query("SELECT ph.place FROM PlaceHistoryEntity ph WHERE ph.user.id = :userId")
    fun findPlacesByUserId(userId: Long, pageable: Pageable): Page<PlaceEntity>

    @Query("SELECT ph FROM PlaceHistoryEntity ph WHERE ph.user.id = :userId ORDER BY ph.createdAt DESC")
    fun findByUserId(userId: Long, pageable: Pageable): Page<PlaceHistoryEntity>
}