package io.miinhho.recomran.place

import io.miinhho.recomran.api.KakaoAPIService
import io.miinhho.recomran.common.response.APIResponse
import io.miinhho.recomran.common.response.APIResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/place")
class PlaceController(private val kakaoAPIService: KakaoAPIService) {

    @GetMapping("/search")
    fun getPlaceFromKakaoAPI(
        @RequestParam("x") x: Double,
        @RequestParam("y") y: Double,
        @RequestParam("radius") radius: Int,
        @RequestParam("page", required = false) page: Int?,
        @RequestParam("size", required = false) size: Int?
    ): APIResponseEntity {
        val places = kakaoAPIService.getPlaces(x, y, radius, page, size)
        val randomPlace = KakaoAPIService.getRandomPlace(places)
        return APIResponse
            .success(data = randomPlace)
    }
}