package io.miinhho.recomran.place.model

import io.miinhho.recomran.api.dto.PlaceDocument

data class Place(
    val id: Long? = null,
    val name: String,
    val category: String,
    val phone: String,
    val address: String,
    val roadAddress: String,
    val url: String,
)

fun PlaceDocument.toPlace(): Place {
    return Place(
        id = this.id.toLong(),
        name = this.name,
        category = this.category,
        phone = this.phone,
        address = this.address,
        roadAddress = this.roadAddress,
        url = this.url
    )
}
