package io.miinhho.recomran.history

import io.miinhho.recomran.common.entity.BaseTimeEntity
import io.miinhho.recomran.place.Place
import io.miinhho.recomran.user.User
import jakarta.persistence.*

@Entity
@Table(name = "place_history")
data class PlaceHistory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    var place: Place,
) : BaseTimeEntity()
