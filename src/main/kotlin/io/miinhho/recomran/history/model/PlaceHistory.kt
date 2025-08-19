package io.miinhho.recomran.history.model

import io.miinhho.recomran.place.model.Place
import io.miinhho.recomran.user.model.User
import java.time.LocalDateTime

data class PlaceHistory(
    val id: Long? = null,
    val user: User,
    val place: Place,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
