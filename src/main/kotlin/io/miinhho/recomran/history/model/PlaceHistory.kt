package io.miinhho.recomran.history.model

import io.miinhho.recomran.place.model.Place
import java.time.LocalDateTime

data class PlaceHistory(
    val id: Long? = null,
    val userId: Long,
    val place: Place,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

fun Place.toPlaceHistory(userId: Long): PlaceHistory {
    return PlaceHistory(
        userId = userId,
        place = this
    )
}
