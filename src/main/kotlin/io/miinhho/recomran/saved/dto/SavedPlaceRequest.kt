package io.miinhho.recomran.saved.dto

import io.miinhho.recomran.place.model.Place

data class AddSavedPlaceRequest(
    val name: String,
)

data class PlaceDto(
    val id: Long,
    val name: String,
    val category: String,
    val phone: String,
    val address: String,
    val roadAddress: String,
    val url: String,
) {
    fun toPlace(): Place {
        return Place(
            id = id,
            name = name,
            category = category,
            phone = phone,
            address = address,
            roadAddress = roadAddress,
            url = url,
        )
    }
}

data class AddPlaceToSavedPlaceRequest(
    val places: List<PlaceDto>
)

data class DeletePlacesFromSavedPlaceRequest(
    val placeIds: List<Long>
)

data class UpdateSavedPlaceNameRequest(
    val newName: String,
)

data class GetSavedPlaceContainsCertainPlaceRequest(
    val placeId: Long,
)