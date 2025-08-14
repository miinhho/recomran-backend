package io.miinhho.recomran.user

import com.fasterxml.jackson.annotation.JsonIgnore
import io.miinhho.recomran.history.PlaceHistory
import io.miinhho.recomran.saved.SavedPlace
import jakarta.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long? = null,

    var email: String,

    @JsonIgnore
    var password: String,

    var username: String? = null,
    var image: String? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var savedPlaces: MutableList<SavedPlace> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var historyPlace: MutableList<PlaceHistory> = mutableListOf(),
)
