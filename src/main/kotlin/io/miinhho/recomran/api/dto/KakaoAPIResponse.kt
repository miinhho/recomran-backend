package io.miinhho.recomran.api.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoPlaceResponse(
    val documents: List<PlaceDocument>,
    val meta: Meta
)

data class PlaceDocument(
    val id: String,
    @JsonProperty("place_name")
    val placeName: String,
    @JsonProperty("category_name")
    val categoryName: String,
    val phone: String,
    @JsonProperty("address_name")
    val addressName: String,
    @JsonProperty("road_address_name")
    val roadAddressName: String,
    val x: String,
    val y: String,
    @JsonProperty("place_url")
    val placeUrl: String,
    val distance: String,
)

data class Meta(
    @JsonProperty("total_count")
    val totalCount: Int,
    @JsonProperty("pageable_count")
    val pageableCount: Int,
    @JsonProperty("is_end")
    val isEnd: Boolean
)
