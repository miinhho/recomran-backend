package io.miinhho.recomran.user.repository

import com.fasterxml.jackson.annotation.JsonIgnore
import io.miinhho.recomran.history.repository.PlaceHistoryEntity
import io.miinhho.recomran.saved.repository.SavedPlaceEntity
import io.miinhho.recomran.user.model.Email
import io.miinhho.recomran.user.model.Password
import io.miinhho.recomran.user.model.User
import io.miinhho.recomran.user.model.UserRole
import io.miinhho.recomran.user.model.Username
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    var email: String,

    @JsonIgnore
    @Column(nullable = false)
    var hashedPassword: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: UserRole = UserRole.USER,

    @Column(length = 50)
    var username: String? = null,

    @Column
    var image: String? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var savedPlaces: MutableList<SavedPlaceEntity> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var historyPlace: MutableList<PlaceHistoryEntity> = mutableListOf()
) {
    fun toDomain(): User {
        return User(
            id = this.id,
            email = Email.from(this.email),
            hashedPassword = Password.from(this.hashedPassword),
            role = this.role,
            username = this.username?.let { Username(it) },
            image = this.image,
            savedPlaces = this.savedPlaces.map { it.toDomain() },
            historyPlace = this.historyPlace.map { it.toDomain() }
        )
    }

    companion object {
        fun fromDomain(user: User): UserEntity {
            val entity = UserEntity(
                id = user.id,
                email = user.email.value,
                hashedPassword = user.password,
                role = user.role,
                username = user.username?.value,
                image = user.image
            )

            entity.savedPlaces = user.savedPlaces
                .map { SavedPlaceEntity.fromDomain(it, entity) }
                .toMutableList()

            entity.historyPlace = user.historyPlace
                .map { PlaceHistoryEntity.fromDomain(it, entity) }
                .toMutableList()

            return entity
        }
    }
}