package io.miinhho.recomran.saved.service

import io.miinhho.recomran.place.model.Place
import io.miinhho.recomran.saved.exception.SavedPlaceInvalidNameException
import io.miinhho.recomran.saved.exception.SavedPlaceNotOwnerException
import io.miinhho.recomran.saved.model.SavedPlace
import io.miinhho.recomran.saved.model.SavedPlaceName
import io.miinhho.recomran.saved.repository.SavedPlaceRepository
import io.miinhho.recomran.user.model.User
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SavedPlaceServiceImpl(
    private val savedPlaceRepository: SavedPlaceRepository
) : SavedPlaceService {
    override fun getAllSavedPlaces(userId: Long): List<SavedPlace> {
        return savedPlaceRepository.findAllByUserId(userId)
    }

    override fun getSavedPlaces(
        userId: Long,
        pageable: Pageable
    ): List<SavedPlace> {
        return savedPlaceRepository.findByUserId(userId, pageable)
    }

    override fun getSavedPlacesWithPlaceId(userId: Long, placeId: Long): List<SavedPlace> {
        return savedPlaceRepository.findAllByUserId(userId).filter {
            it.places.any { (id) ->
                it.containsPlace(id!!)
            }
        }
    }

    @Transactional
    override fun addSavedPlace(userId: Long, name: String) {
        try {
            val savedPlaceName = SavedPlaceName(name)
            val savedPlace = SavedPlace(userId = userId, name = savedPlaceName)
            savedPlaceRepository.save(savedPlace)
        } catch (e: IllegalArgumentException) {
            throw SavedPlaceInvalidNameException(e)
        }
    }

    override fun getPlaces(user: User, id: Long, pageable: Pageable): List<Place> {
        val savedPlace = savedPlaceRepository.findById(id)
        // 해당 사용자가 saved place 에 접근할 수 있는지 확인
        if (savedPlace.userId != user.id || user.isAdmin()) {
            throw SavedPlaceNotOwnerException()
        }
        return savedPlaceRepository.findPlacesBySavedPlaceId(id, pageable)
    }

    @Transactional
    override fun addPlaces(id: Long, places: List<Place>) {
        val savedPlace = savedPlaceRepository.findById(id)
        places.forEach { place ->
            savedPlace.addPlace(place)
        }
        savedPlaceRepository.save(savedPlace)
    }

    @Transactional
    override fun deletePlaces(id: Long, placeIds: List<Long>) {
        val savedPlace = savedPlaceRepository.findById(id)
        placeIds.forEach { id ->
            savedPlace.removePlace(placeId = id)
        }
        savedPlaceRepository.save(savedPlace)
    }

    @Transactional
    override fun updateName(id: Long, newName: String) {
        val savedPlace = savedPlaceRepository.findById(id)
        try {
            val newName = SavedPlaceName(newName)
            savedPlace.updateName(newName)
            savedPlaceRepository.save(savedPlace)
        } catch (e: IllegalArgumentException) {
            throw SavedPlaceInvalidNameException(e)
        }
    }
}