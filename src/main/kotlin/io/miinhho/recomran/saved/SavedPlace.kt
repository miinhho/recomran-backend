package io.miinhho.recomran.saved

import io.miinhho.recomran.common.entity.BaseTimeEntity
import io.miinhho.recomran.place.Place
import io.miinhho.recomran.user.User
import jakarta.persistence.*

@Entity
data class SavedPlace(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "saved_place_places",
        joinColumns = [JoinColumn(name = "saved_place_id")],
        inverseJoinColumns = [JoinColumn(name = "place_id")]
    )
    var places: MutableList<Place> = mutableListOf(),
) : BaseTimeEntity()
