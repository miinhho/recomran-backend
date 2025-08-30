package io.miinhho.recomran.place.repository.impl

import io.miinhho.recomran.place.repository.PlaceRepository
import io.miinhho.recomran.place.model.Place
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class JpaPlaceRepository(
    private val placeEntityRepository: PlaceEntityRepository
) : PlaceRepository {
    override fun findById(id: Long): Place? {
        return placeEntityRepository.findById(id)
            .orElse(null)?.toDomain()
    }

    override fun findByName(name: String, pageable: Pageable): List<Place> {
        return placeEntityRepository.findByNameContaining(name, pageable)
            .map { it.toDomain() }.toList()
    }

    @Transactional
    override fun save(entity: Place): Place {
        val newEntity = PlaceEntity.fromDomain(entity)
        val savedEntity = placeEntityRepository.save(newEntity)
        return savedEntity.toDomain()
    }

    @Transactional
    override fun delete(id: Long) {
        placeEntityRepository.deleteById(id)
    }
}

interface PlaceEntityRepository: JpaRepository<PlaceEntity, Long> {
    fun findByNameContaining(placeName: String, pageable: Pageable): Page<PlaceEntity>
}