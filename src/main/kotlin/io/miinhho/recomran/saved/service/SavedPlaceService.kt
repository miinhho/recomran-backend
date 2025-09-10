package io.miinhho.recomran.saved.service

import io.miinhho.recomran.place.model.Place
import io.miinhho.recomran.saved.exception.SavedPlaceNotOwnerException
import io.miinhho.recomran.saved.model.SavedPlace
import io.miinhho.recomran.saved.model.SavedPlaceName
import io.miinhho.recomran.saved.repository.SavedPlaceRepository
import io.miinhho.recomran.user.model.User
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SavedPlaceService(private val savedPlaceRepository: SavedPlaceRepository) {
    fun getAllSavedPlaces(userId: Long): List<SavedPlace> {
        return savedPlaceRepository.findAllByUserId(userId)
    }

    fun getSavedPlaces(
        userId: Long,
        pageable: Pageable
    ): List<SavedPlace> {
        return savedPlaceRepository.findByUserId(userId, pageable)
    }

    fun getSavedPlacesWithPlaceId(userId: Long, placeId: Long): List<SavedPlace> {
        return savedPlaceRepository.findAllByUserId(userId).filter {
            it.places.any { (id) ->
                it.containsPlace(id!!)
            }
        }
    }

    @Transactional
    fun addSavedPlace(userId: Long, name: String) {
        val savedPlaceName = SavedPlaceName.from(name)
        val savedPlace = SavedPlace(userId = userId, name = savedPlaceName)
        savedPlaceRepository.save(savedPlace)
    }

    @Transactional
    fun deleteSavedPlace(user: User, id: Long) {
        val savedPlace = savedPlaceRepository.findById(id)
        // 해당 사용자가 접근할 수 있는지 확인
        if (savedPlace.userId != user.id || user.isAdmin()) {
            throw SavedPlaceNotOwnerException()
        }
        savedPlaceRepository.delete(id)
    }

    fun getPlaces(user: User, id: Long, pageable: Pageable): List<Place> {
        val savedPlace = savedPlaceRepository.findById(id)
        // 해당 사용자가 접근할 수 있는지 확인
        if (savedPlace.userId != user.id || user.isAdmin()) {
            throw SavedPlaceNotOwnerException()
        }
        return savedPlaceRepository.findPlacesBySavedPlaceId(id, pageable)
    }

    @Transactional
    fun addPlaces(id: Long, places: List<Place>) {
        val savedPlace = savedPlaceRepository.findById(id)
        places.forEach { place ->
            savedPlace.addPlace(place)
        }
        savedPlaceRepository.save(savedPlace)
    }

    @Transactional
    fun deletePlaces(id: Long, placeIds: List<Long>) {
        val savedPlace = savedPlaceRepository.findById(id)
        placeIds.forEach { id ->
            savedPlace.removePlace(placeId = id)
        }
        savedPlaceRepository.save(savedPlace)
    }

    @Transactional
    fun updateName(id: Long, newName: String) {
        val savedPlace = savedPlaceRepository.findById(id)
        savedPlace.updateName(newName)
        savedPlaceRepository.save(savedPlace)
    }
}