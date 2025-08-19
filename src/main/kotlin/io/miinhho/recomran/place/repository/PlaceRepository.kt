package io.miinhho.recomran.place.repository

import io.miinhho.recomran.common.repository.CommonRepository
import io.miinhho.recomran.place.model.Place
import org.springframework.data.domain.Pageable

interface PlaceRepository : CommonRepository<Place> {
    fun findById(id: Long): Place?
    fun findByName(name: String, pageable: Pageable): List<Place>
}