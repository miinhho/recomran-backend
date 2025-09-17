package io.miinhho.recomran.saved.repository

import io.miinhho.recomran.place.model.Place
import io.miinhho.recomran.place.repository.PlaceEntity
import io.miinhho.recomran.saved.exception.SavedPlaceNotFoundException
import io.miinhho.recomran.saved.model.SavedPlace
import io.miinhho.recomran.user.repository.UserEntity
import io.miinhho.recomran.user.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
class SavedPlaceRepository(
    private val savedPlaceEntityRepository: SavedPlaceEntityRepository,
    private val userRepository: UserRepository
) {
    fun findById(id: Long): SavedPlace {
        return savedPlaceEntityRepository.findById(id)
            .orElseThrow { SavedPlaceNotFoundException() }.toDomain()
    }

    fun findByUserId(userId: Long, pageable: Pageable): List<SavedPlace> {
        val savedPlaceIds = savedPlaceEntityRepository
            .findSavedPlaceIdsByUserId(userId, pageable)
            .toList()
        val savedPlaces = savedPlaceEntityRepository
            .findSavedPlacesWithPlacesByIds(savedPlaceIds)

        return savedPlaces.map { it.toDomain() }
    }

    fun findAllByUserId(userId: Long): List<SavedPlace> {
        return savedPlaceEntityRepository.findAllSavedPlacesByUserId(userId)
            .map { it.toDomain() }
    }

    fun findPlacesBySavedPlaceId(id: Long, pageable: Pageable): List<Place> {
        return savedPlaceEntityRepository.findPlacesBySavedPlaceId(id, pageable)
            .map { it.toDomain() }.toList()
    }

    fun findPlacesByUserId(userId: Long, pageable: Pageable): List<Place> {
        return savedPlaceEntityRepository.findPlacesByUserId(userId, pageable)
            .map { it.toDomain() }.toList()
    }

    fun save(entity: SavedPlace): SavedPlace {
        val user = userRepository.findById(entity.userId)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다")
        val newEntity = SavedPlaceEntity.fromDomain(
            savedPlace = entity,
            userEntity = UserEntity.fromDomain(user)
        )
        val savedEntity = savedPlaceEntityRepository.save(newEntity)
        return savedEntity.toDomain()
    }

    fun delete(id: Long) {
        savedPlaceEntityRepository.deleteById(id)
    }
}

interface SavedPlaceEntityRepository: JpaRepository<SavedPlaceEntity, Long> {
    @Query("""
        SELECT DISTINCT spp.place FROM SavedPlaceEntity sp
        JOIN sp.savedPlacePlaces spp
        WHERE sp.user.id = :userId
        ORDER BY spp.place.name ASC
    """)
    fun findPlacesByUserId(userId: Long, pageable: Pageable): Page<PlaceEntity>

    @Query("""
        SELECT spp.place FROM SavedPlaceEntity sp
        JOIN sp.savedPlacePlaces spp
        WHERE sp.id = :savedPlaceId
        ORDER BY spp.place.name ASC
    """)
    fun findPlacesBySavedPlaceId(savedPlaceId: Long, pageable: Pageable): Page<PlaceEntity>

    @Query("""
        SELECT sp.id FROM SavedPlaceEntity sp
        WHERE sp.user.id = :userId
        ORDER BY sp.name ASC
    """)
    fun findSavedPlaceIdsByUserId(userId: Long, pageable: Pageable): Page<Long>

    @Query("""
        SELECT DISTINCT sp FROM SavedPlaceEntity sp
        JOIN FETCH sp.savedPlacePlaces spp
        JOIN FETCH spp.place
        WHERE sp.id IN :ids
    """)
    fun findSavedPlacesWithPlacesByIds(ids: List<Long>): List<SavedPlaceEntity>

    @Query("""
        SELECT sp FROM SavedPlaceEntity sp 
        JOIN FETCH sp.savedPlacePlaces spp
        JOIN FETCH spp.place
        WHERE sp.user.id = :userId
    """)
    fun findAllSavedPlacesByUserId(userId: Long): List<SavedPlaceEntity>
}
