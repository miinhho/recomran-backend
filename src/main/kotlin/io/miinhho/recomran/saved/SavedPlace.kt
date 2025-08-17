package io.miinhho.recomran.saved

import io.miinhho.recomran.common.entity.BaseTimeEntity
import io.miinhho.recomran.place.Place
import io.miinhho.recomran.user.User
import jakarta.persistence.*

@Entity
@Table(name = "saved_place")
class SavedPlace(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saved_place_id")
    var id: Long? = null,

    var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        joinColumns = [JoinColumn(name = "saved_place_id")],
        inverseJoinColumns = [JoinColumn(name = "place_id")]
    )
    var places: MutableList<Place> = mutableListOf(),
) : BaseTimeEntity()
