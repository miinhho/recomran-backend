package io.miinhho.recomran.place.repository

import io.miinhho.recomran.place.model.Place
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class PlaceRepository(private val placeEntityRepository: PlaceEntityRepository) {
    fun findById(id: Long): Place? {
        return placeEntityRepository.findById(id)
            .orElse(null)?.toDomain()
    }

    fun findByName(name: String, pageable: Pageable): MutableList<Place> {
        return placeEntityRepository.findByNameContaining(name, pageable)
            .map { it.toDomain() }.toList()
    }

    fun save(entity: Place): Place {
        val newEntity = PlaceEntity.fromDomain(entity)
        val savedEntity = placeEntityRepository.save(newEntity)
        return savedEntity.toDomain()
    }

    fun delete(id: Long) {
        placeEntityRepository.deleteById(id)
    }
}

interface PlaceEntityRepository: JpaRepository<PlaceEntity, Long> {
    fun findByNameContaining(placeName: String, pageable: Pageable): Page<PlaceEntity>
}