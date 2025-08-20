package io.miinhho.recomran.history.model

import io.miinhho.recomran.place.model.Place
import java.time.LocalDateTime

data class PlaceHistory(
    val id: Long? = null,
    val userId: Long,
    val place: Place,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
