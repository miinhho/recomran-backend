package io.miinhho.recomran.saved.service

import io.miinhho.recomran.place.model.Place
import io.miinhho.recomran.saved.model.SavedPlace
import io.miinhho.recomran.user.model.User
import org.springframework.data.domain.Pageable

interface SavedPlaceService {
    fun getAllSavedPlaces(userId: Long): List<SavedPlace>
    fun getSavedPlaces(userId: Long, pageable: Pageable): List<SavedPlace>
    fun getSavedPlacesWithPlaceId(userId: Long, placeId: Long): List<SavedPlace>
    fun addSavedPlace(userId: Long, name: String)
    fun getPlaces(user: User, id: Long, pageable: Pageable): List<Place>
    fun addPlaces(id: Long, places: List<Place>)
    fun deletePlaces(id: Long, placeIds: List<Long>)
    fun updateName(id: Long, newName: String)
}