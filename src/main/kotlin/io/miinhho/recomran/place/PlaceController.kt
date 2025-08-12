package io.miinhho.recomran.place

import io.miinhho.recomran.api.KakaoAPIService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/place")
class PlaceController(
    val kakaoAPIService: KakaoAPIService
) {

    @GetMapping("/search")
    fun getPlaceFromKakaoAPI(
        @RequestParam("x") x: Double,
        @RequestParam("y") y: Double,
        @RequestParam("radius") radius: Int,
        @RequestParam("page", required = false) page: Int?,
        @RequestParam("size", required = false) size: Int?
    ) {
        val places = kakaoAPIService.getPlaces(x, y, radius, page, size)
        TODO("places 중에서 랜덤 1개를 뽑고 반환하기")
    }
}