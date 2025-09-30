package io.miinhho.recomran.place

import io.miinhho.recomran.api.KakaoAPIService
import io.miinhho.recomran.common.response.APIResponse
import io.miinhho.recomran.common.response.APIResponseEntity
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/place")
@Tag(name = "Place API")
class PlaceController(
    private val kakaoAPIService: KakaoAPIService,
) {
    @GetMapping("/search")
    @Operation(
        summary = "Kakao API 에서 무작위 장소를 선별해 반환합니다",
        description = "page: 1~45, size: 1~15"
    )
    fun getPlaceFromKakaoAPI(
        @RequestParam("x") x: Double,
        @RequestParam("y") y: Double,
        @RequestParam("radius") radius: Int,
        @RequestParam("page") page: Int?,
        @RequestParam("size") size: Int?,
    ): APIResponseEntity {
        val places = kakaoAPIService.getPlaces(x, y, radius, page, size)
        val randomPlace = KakaoAPIService.getRandomPlace(places)
        return APIResponse.success(data = randomPlace)
    }
}