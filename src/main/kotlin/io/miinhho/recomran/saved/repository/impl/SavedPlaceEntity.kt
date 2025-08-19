package io.miinhho.recomran.saved.repository.impl

import io.miinhho.recomran.place.repository.impl.PlaceEntity
import io.miinhho.recomran.saved.model.SavedPlace
import io.miinhho.recomran.saved.model.SavedPlaceName
import io.miinhho.recomran.user.repository.impl.UserEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import kotlin.collections.map

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "saved_place_places",
        joinColumns = [JoinColumn(name = "saved_place_id")],
        inverseJoinColumns = [JoinColumn(name = "place_id")]
    )
    var places: MutableList<PlaceEntity> = mutableListOf(),
) {
    fun toDomain(): SavedPlace {
        return SavedPlace(
            id = this.id,
            name = SavedPlaceName(this.name),
            userId = this.user.id!!,
            places = this.places.map { it.toDomain() },
        )
    }

    companion object {
        fun fromDomain(savedPlace: SavedPlace, userEntity: UserEntity): SavedPlaceEntity {
            val entity = SavedPlaceEntity(
                id = savedPlace.id,
                name = savedPlace.name.value,
                user = userEntity,
            )

            entity.places = savedPlace.places
                .map { PlaceEntity.fromDomain(it) }
                .toMutableList()

            return entity
        }
    }
}