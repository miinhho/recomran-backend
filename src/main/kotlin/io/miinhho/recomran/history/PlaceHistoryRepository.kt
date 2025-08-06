package io.miinhho.recomran.history

import io.miinhho.recomran.place.Place
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaceHistoryRepository: JpaRepository<PlaceHistory, Long> {
    @Query("SELECT ph.place FROM PlaceHistory ph WHERE ph.user.id = :userId")
    fun findPlacesByUserId(userId: Long, pageable: Pageable): Page<Place>

    fun findByUserIdOrderByCreatedAtDesc(userId: Long, pageable: Pageable): Page<PlaceHistory>
}