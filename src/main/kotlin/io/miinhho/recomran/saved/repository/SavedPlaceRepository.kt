package io.miinhho.recomran.saved.repository

import io.miinhho.recomran.common.repository.CommonRepository
import io.miinhho.recomran.place.model.Place
import io.miinhho.recomran.saved.model.SavedPlace
import org.springframework.data.domain.Pageable

interface SavedPlaceRepository : CommonRepository<SavedPlace> {
    fun findById(id: Long): SavedPlace
    fun findPlacesBySavedPlaceId(id: Long, pageable: Pageable): List<Place>
    fun findPlacesByUserId(userId: Long, pageable: Pageable): List<Place>
    fun findByUserId(userId: Long, pageable: Pageable): List<SavedPlace>
}