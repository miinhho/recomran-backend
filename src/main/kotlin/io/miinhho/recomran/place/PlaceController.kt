package io.miinhho.recomran.place

import io.miinhho.recomran.api.KakaoAPIService
import io.miinhho.recomran.common.response.APIResponse
import io.miinhho.recomran.common.response.APIResponseEntity
import io.miinhho.recomran.user.model.User
import io.miinhho.recomran.history.PlaceHistoryService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/place")
class PlaceController(
    private val kakaoAPIService: KakaoAPIService,
    private val placeHistoryService: PlaceHistoryService
) {
    @GetMapping("/search")
    fun getPlaceFromKakaoAPI(
        @RequestParam("x") x: Double,
        @RequestParam("y") y: Double,
        @RequestParam("radius") radius: Int,
        @PageableDefault pageable: Pageable,
        @AuthenticationPrincipal user: User,
    ): APIResponseEntity {
        val places = kakaoAPIService.getPlaces(x, y, radius, pageable.pageNumber, pageable.pageSize)
        val randomPlace = KakaoAPIService.getRandomPlace(places)
        // 유저가 뽑은 장소 저장
        placeHistoryService.savePlaceToHistory(user, randomPlace)
        return APIResponse.success(data = randomPlace)
    }
}