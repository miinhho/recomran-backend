package io.miinhho.recomran.history

import io.miinhho.recomran.history.model.PlaceHistory
import io.miinhho.recomran.history.model.toPlaceHistory
import io.miinhho.recomran.history.repository.PlaceHistoryRepository
import io.miinhho.recomran.place.model.Place
import io.miinhho.recomran.user.model.User
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PlaceHistoryService(private val placeHistoryRepository: PlaceHistoryRepository) {
    @Transactional
    fun savePlaceToHistory(user: User, place: Place): PlaceHistory {
        // Security Filter 단에서 user.id 는 Non-Null 보장
        val placeHistory = place.toPlaceHistory(user.id!!)
        return placeHistoryRepository.save(placeHistory)
    }
}