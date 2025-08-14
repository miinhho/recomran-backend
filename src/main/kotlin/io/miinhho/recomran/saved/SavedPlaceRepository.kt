package io.miinhho.recomran.saved

import io.miinhho.recomran.place.Place
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SavedPlaceRepository: JpaRepository<SavedPlace, Long> {
    @Query("SELECT DISTINCT p FROM SavedPlace sp JOIN sp.places p WHERE sp.user.id = :userId")
    fun findPlacesByUserId(userId: Long, pageable: Pageable): Page<Place>

    @Query("SELECT p FROM SavedPlace sp JOIN sp.places p WHERE sp.id = :savedPlaceId")
    fun findPlacesBySavedPlaceId(savedPlaceId: Long, pageable: Pageable): Page<Place>
}