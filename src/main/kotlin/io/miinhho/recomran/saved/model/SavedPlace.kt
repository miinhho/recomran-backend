package io.miinhho.recomran.saved.model

import io.miinhho.recomran.place.model.Place
import kotlin.collections.filter

data class SavedPlace(
    val id: Long? = null,
    val name: SavedPlaceName,
    val userId: Long,
    val places: List<Place> = emptyList(),
) {
    fun addPlace(place: Place): SavedPlace {
        require(!places.any { it.id == place.id }) { "이미 추가된 장소입니다" }
        return this.copy(places = places + place)
    }

    fun removePlace(placeId: Long): SavedPlace {
        return this.copy(places = places.filter { it.id != placeId })
    }

    fun updateName(newName: SavedPlaceName): SavedPlace {
        return this.copy(name = newName)
    }

    fun containsPlace(placeId: Long): Boolean = places.any { it.id == placeId }
}
