package io.miinhho.recomran.api.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoPlaceResponse(
    val documents: List<PlaceDocument>,
    val meta: Meta
)

data class PlaceDocument(
    val id: String,
    @JsonProperty("place_name")
    val name: String,
    @JsonProperty("category_name")
    val category: String,
    val phone: String,
    @JsonProperty("address_name")
    val address: String,
    @JsonProperty("road_address_name")
    val roadAddress: String,
    val x: String,
    val y: String,
    @JsonProperty("place_url")
    val url: String,
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
