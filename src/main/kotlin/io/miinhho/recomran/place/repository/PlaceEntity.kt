package io.miinhho.recomran.place.repository

import io.miinhho.recomran.place.model.Place
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "place")
class PlaceEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    var id: Long? = null,

    @Column(nullable = false, length = 200)
    var name: String,

    var category: String,
    var phone: String,

    @Column(length = 500)
    var address: String,

    @Column(length = 500)
    var roadAddress: String,

    var url: String,
) {
    fun toDomain(): Place {
        return Place(
            id = this.id,
            name = this.name,
            address = this.address,
            roadAddress = this.roadAddress,
            category = this.category,
            phone = this.phone,
            url = this.url
        )
    }

    companion object {
        fun fromDomain(place: Place): PlaceEntity {
            return PlaceEntity(
                id = place.id,
                name = place.name,
                address = place.address,
                roadAddress = place.roadAddress,
                category = place.category,
                phone = place.phone,
                url = place.url
            )
        }
    }
}