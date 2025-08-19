package io.miinhho.recomran.saved.repository.impl

import io.miinhho.recomran.place.model.Place
import io.miinhho.recomran.place.repository.impl.PlaceEntity
import io.miinhho.recomran.saved.repository.SavedPlaceRepository
import io.miinhho.recomran.saved.model.SavedPlace
import io.miinhho.recomran.user.repository.impl.UserEntityRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
class JpaSavedPlaceRepository(
    private val savedPlaceEntityRepository: SavedPlaceEntityRepository,
    private val userEntityRepository: UserEntityRepository
) : SavedPlaceRepository {
    override fun findById(id: Long): SavedPlace? {
        return savedPlaceEntityRepository.findById(id)
            .orElse(null)?.toDomain()
    }

    override fun findByUserId(userId: Long, pageable: Pageable): List<SavedPlace> {
        return savedPlaceEntityRepository.findSavedPlacesByUserId(userId, pageable)
            .map { it.toDomain() }.toList()
    }

    override fun findPlacesBySavedPlaceId(id: Long, pageable: Pageable): List<Place> {
        return savedPlaceEntityRepository.findPlacesBySavedPlaceId(id, pageable)
            .map { it.toDomain() }.toList()
    }

    override fun findPlacesByUserId(userId: Long, pageable: Pageable): List<Place> {
        return savedPlaceEntityRepository.findPlacesByUserId(userId, pageable)
            .map { it.toDomain() }.toList()
    }

    override fun save(entity: SavedPlace): SavedPlace {
        val userEntity = userEntityRepository.findById(entity.userId)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다") }

        val newEntity = SavedPlaceEntity.fromDomain(entity, userEntity)
        val savedEntity = savedPlaceEntityRepository.save(newEntity)
        return savedEntity.toDomain()
    }

    override fun delete(id: Long) {
        savedPlaceEntityRepository.deleteById(id)
    }
}

interface SavedPlaceEntityRepository: JpaRepository<SavedPlaceEntity, Long> {
    @Query("SELECT DISTINCT p FROM SavedPlaceEntity sp JOIN sp.places p WHERE sp.user.id = :userId ORDER BY p.name ASC")
    fun findPlacesByUserId(userId: Long, pageable: Pageable): Page<PlaceEntity>

    @Query("SELECT p FROM SavedPlaceEntity sp JOIN sp.places p WHERE sp.id = :savedPlaceId ORDER BY p.name ASC")
    fun findPlacesBySavedPlaceId(savedPlaceId: Long, pageable: Pageable): Page<PlaceEntity>

    @Query("SELECT sp FROM SavedPlaceEntity sp WHERE sp.user.id = :userId ORDER BY sp.name ASC")
    fun findSavedPlacesByUserId(userId: Long, pageable: Pageable): Page<SavedPlaceEntity>
}