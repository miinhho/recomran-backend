package io.miinhho.recomran.history.repository

import io.miinhho.recomran.history.model.PlaceHistory
import io.miinhho.recomran.place.repository.PlaceEntity
import io.miinhho.recomran.user.repository.UserEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "place_history")
class PlaceHistoryEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    var place: PlaceEntity,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun toDomain(): PlaceHistory {
        return PlaceHistory(
            id = this.id,
            userId = this.user.id!!,
            place = this.place.toDomain(),
            createdAt = this.createdAt
        )
    }

    companion object {
        fun fromDomain(
            placeHistory: PlaceHistory,
            userEntity: UserEntity
        ): PlaceHistoryEntity {
            return PlaceHistoryEntity(
                id = placeHistory.id,
                user = userEntity,
                place = PlaceEntity.fromDomain(placeHistory.place),
                createdAt = placeHistory.createdAt
            )
        }
    }
}