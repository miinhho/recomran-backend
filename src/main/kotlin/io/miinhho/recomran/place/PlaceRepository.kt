package io.miinhho.recomran.place

import org.springframework.data.repository.CrudRepository

interface PlaceRepository: CrudRepository<Place, Long> {
    fun findByPlaceName(placeName: String): Place?
}