package io.miinhho.recomran.api

import io.miinhho.recomran.api.dto.KakaoPlaceResponse
import io.miinhho.recomran.api.dto.PlaceDocument
import io.miinhho.recomran.api.exception.KakaoAPIException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import kotlin.random.Random

@Service
class KakaoAPIService(
    @Value($$"${kakao_api.key}") private val kakaoAPIKey: String,
    @Value($$"${kakao_api.url}") private val kakaoAPIUrl: String
) {
    companion object {
        /**
         * Kakao API 의 음식점 카테고리
         */
        const val CATEGORY_CODE: String = "FD6"

        fun getRandomPlace(places: List<PlaceDocument>): PlaceDocument {
            val randomIndex = Random.nextInt(places.size)
            return places[randomIndex]
        }
    }

    /**
     * Kakao API 에서 `radius` 이내의 음식점을 조회합니다.
     *
     * @param x 중심 좌표의 경도
     * @param y 중심 좌표의 위도
     * @param radius 검색 반경(미터)
     * @param page 페이지 번호 (nullable)
     * @param size 페이지 크기 (nullable)
     * @return 음식점 목록
     * @throws io.miinhho.recomran.api.exception.KakaoAPIException - Kakao API 의 응답이 유효하지 않았을 때
     */
    fun getPlaces(x: Double, y: Double, radius: Int, page: Int?, size: Int?): List<PlaceDocument> {
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
            .queryParam("category_group_code", KakaoAPIService.CATEGORY_CODE)
            .queryParam("x", x)
            .queryParam("y", y)
            .queryParam("radius", radius)

        page?.let { builder.queryParam("page", it) }
        size?.let { builder.queryParam("size", it) }

        return builder.build().toUri()
    }
}