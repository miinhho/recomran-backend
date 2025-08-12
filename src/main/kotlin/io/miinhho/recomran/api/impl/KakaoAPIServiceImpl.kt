package io.miinhho.recomran.api.impl

import io.miinhho.recomran.api.dto.PlaceDocument
import io.miinhho.recomran.api.exception.KakaoAPIException
import io.miinhho.recomran.api.KakaoAPIService
import io.miinhho.recomran.api.dto.KakaoPlaceResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

// Kakao API 의 음식점 카테고리
private const val CATEGORY_CODE = "FD6"

class KakaoAPIServiceImpl(
    @Value($$"${kakao_api.key}") private val kakaoAPIKey: String,
    @Value($$"${kakao_api.url}") private val kakaoAPIUrl: String
): KakaoAPIService {
    override fun getPlaces(x: Double, y: Double, radius: Int, page: Int?, size: Int?): List<PlaceDocument> {
        val apiUri = getApiUri(x, y, radius, page, size)

        val restClient = RestClient.create()
        val response = restClient.get()
            .uri(apiUri)
            .header("Authorization", "KakaoAK $kakaoAPIKey")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body<KakaoPlaceResponse>()

        return response?.documents ?: throw KakaoAPIException()
    }

    /**
     * @param x - 중심 좌표의 X 혹은 경도(longitude) 값
     * @param y - 중심 좌표의 Y 혹은 위도(latitude) 값
     * @param radius - 중심 좌표로부터의 반경거리, 최소 0m, 최대 20000m
     * @param page - 최소 1, 최대 45, 기본값 1
     * @param size - 최소 1, 최대 15, 기본값 15
     */
    private fun getApiUri(x: Double, y: Double, radius: Int, page: Int?, size: Int?): URI {
        val builder = UriComponentsBuilder.fromUriString(kakaoAPIUrl)
            .queryParam("category_group_code", CATEGORY_CODE)
            .queryParam("x", x)
            .queryParam("y", y)
            .queryParam("radius", radius)

        page?.let { builder.queryParam("page", it) }
        size?.let { builder.queryParam("size", it) }

        return builder.build().toUri()
    }
}