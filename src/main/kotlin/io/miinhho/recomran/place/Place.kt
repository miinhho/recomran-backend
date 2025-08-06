package io.miinhho.recomran.place

import io.miinhho.recomran.history.PlaceHistory
import io.miinhho.recomran.saved.SavedPlace
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany

@Entity
data class Place(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var placeName: String,
    var categoryName: String,
    var phone: String,
    var addressName: String,
    var roadAddressName: String,
    var url: String,

    @OneToMany(mappedBy = "place", cascade = [CascadeType.ALL], orphanRemoval = true)
    var visitedHistories: MutableList<PlaceHistory> = mutableListOf(),

    @ManyToMany(mappedBy = "places")
    var savedPlaces: MutableList<SavedPlace> = mutableListOf(),
)
