package io.miinhho.recomran.place

import org.springframework.data.jpa.repository.JpaRepository

interface PlaceRepository: JpaRepository<Place, Long> {
    fun findByPlaceName(placeName: String): Place?
}