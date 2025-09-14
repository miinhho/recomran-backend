package io.miinhho.recomran.saved.repository

import io.miinhho.recomran.place.repository.PlaceEntity
import io.miinhho.recomran.saved.model.SavedPlace
import io.miinhho.recomran.saved.model.SavedPlaceName
import io.miinhho.recomran.user.repository.UserEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "saved_places")
class SavedPlaceEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saved_place_id")
    var id: Long? = null,

    @Column(nullable = false, length = 100)
    var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,

    @OneToMany(mappedBy = "savedPlace", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var savedPlacePlaces: MutableList<SavedPlacePlaces> = mutableListOf(),
) {
    fun toDomain(): SavedPlace {
        val places = savedPlacePlaces.map { it.place.toDomain() }
        return SavedPlace(
            id = this.id,
            name = SavedPlaceName(this.name),
            userId = this.user.id!!,
            places = places,
        )
    }

    companion object {
        fun fromDomain(savedPlace: SavedPlace, userEntity: UserEntity): SavedPlaceEntity {
            val entity = SavedPlaceEntity(
                id = savedPlace.id,
                name = savedPlace.name.value,
                user = userEntity,
            )

            entity.savedPlacePlaces = savedPlace.places.map {
                place ->
                SavedPlacePlaces(
                    place = PlaceEntity.fromDomain(place),
                    savedPlace = entity
                )
            }.toMutableList()

            return entity
        }
    }
}