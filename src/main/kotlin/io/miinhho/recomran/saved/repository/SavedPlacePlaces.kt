package io.miinhho.recomran.saved.repository

import io.miinhho.recomran.place.repository.PlaceEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "saved_place_places")
class SavedPlacePlaces (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saved_place_places_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    var place: PlaceEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saved_place_id", nullable = false)
    var savedPlace: SavedPlaceEntity,
)
