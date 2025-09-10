package io.miinhho.recomran.saved.dto

import io.miinhho.recomran.place.model.Place
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AddSavedPlaceRequest(
    @field:NotEmpty
    @field:Size(max = 100, message = "저장된 장소 이름은 100자를 초과할 수 없습니다")
    val name: String,
)

data class DeleteSavedPlaceRequest(
    @field:NotNull
    val id: Long,
)

data class PlaceDto(
    @field:NotNull
    val id: Long,
    @field:NotEmpty
    val name: String,
    @field:NotEmpty
    val category: String,
    @field:NotEmpty
    val phone: String,
    @field:NotEmpty
    val address: String,
    @field:NotEmpty
    val roadAddress: String,
    @field:NotEmpty
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
    @field:NotEmpty
    val places: List<PlaceDto>
)

data class DeletePlacesFromSavedPlaceRequest(
    @field:NotEmpty
    val placeIds: List<Long>
)

data class UpdateSavedPlaceNameRequest(
    @field:NotEmpty
    @field:Size(max = 100, message = "저장된 장소 이름은 100자를 초과할 수 없습니다")
    val newName: String,
)

data class GetSavedPlaceContainsCertainPlaceRequest(
    @field:NotNull
    val placeId: Long,
)