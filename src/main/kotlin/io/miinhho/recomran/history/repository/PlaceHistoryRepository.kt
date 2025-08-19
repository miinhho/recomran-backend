package io.miinhho.recomran.history.repository

import io.miinhho.recomran.common.repository.CommonRepository
import io.miinhho.recomran.history.model.PlaceHistory
import io.miinhho.recomran.place.model.Place
import org.springframework.data.domain.Pageable

interface PlaceHistoryRepository : CommonRepository<PlaceHistory> {
    fun findPlacesByUserId(userId: Long, pageable: Pageable): List<Place>
    fun findByUserId(userId: Long, pageable: Pageable): List<PlaceHistory>
}