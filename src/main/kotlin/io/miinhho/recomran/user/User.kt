package io.miinhho.recomran.user

import io.miinhho.recomran.history.PlaceHistory
import io.miinhho.recomran.saved.SavedPlace
import jakarta.persistence.*

@Entity
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var username: String,
    var password: String,
    var image: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var savedPlaces: MutableList<SavedPlace> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var historyPlace: MutableList<PlaceHistory> = mutableListOf(),
)
