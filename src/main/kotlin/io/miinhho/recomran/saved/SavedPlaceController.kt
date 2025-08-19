package io.miinhho.recomran.saved

import io.miinhho.recomran.common.response.APIResponse
import io.miinhho.recomran.common.response.APIResponseEntity
import io.miinhho.recomran.saved.dto.AddSavedPlaceRequest
import io.miinhho.recomran.saved.exception.SavedPlaceNotFoundException
import io.miinhho.recomran.saved.exception.SavedPlaceNotOwnerException
import io.miinhho.recomran.saved.model.SavedPlace
import io.miinhho.recomran.saved.model.SavedPlaceName
import io.miinhho.recomran.saved.repository.SavedPlaceRepository
import io.miinhho.recomran.user.model.User
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val SAVED_PLACE_PAGE_SIZE: Int = 5
const val PLACE_PAGE_SIZE: Int = 10

@RestController
@RequestMapping("/api/saved-place")
class SavedPlaceController(
    private val savedPlaceRepository: SavedPlaceRepository
) {
    @Transactional
    @PostMapping("/add")
    fun addSavedPlace(
        @AuthenticationPrincipal user: User,
        @RequestBody body: AddSavedPlaceRequest
    ): APIResponseEntity {
        val savedPlace = SavedPlace(name = SavedPlaceName(body.name), userId = user.id!!)
        savedPlaceRepository.save(savedPlace)
        return APIResponse.success()
    }

    @GetMapping("/all")
    fun getAllSavedPlaces(
        @AuthenticationPrincipal user: User,
        @PageableDefault(size = SAVED_PLACE_PAGE_SIZE, page = 0) pageable: Pageable
    ): APIResponseEntity {
        val places = savedPlaceRepository.findByUserId(
            userId = user.id!!, pageable = pageable)
        return APIResponse.success(data = places)
    }

    @GetMapping("/{id}/places")
    fun getPlacesFromSavedPlace(
        @AuthenticationPrincipal user: User,
        @PathVariable("id") id: Long,
        @PageableDefault(size = PLACE_PAGE_SIZE, page = 0) pageable: Pageable
    ): APIResponseEntity {
        // 해당 사용자가 saved place 에 접근할 수 있는지 확인
        val savedPlace = savedPlaceRepository.findById(id)
            ?: throw SavedPlaceNotFoundException()
        if (savedPlace.userId != user.id) {
            throw SavedPlaceNotOwnerException()
        }

        val places = savedPlaceRepository.findPlacesBySavedPlaceId(id, pageable)
        return APIResponse.success(data = places)
    }
}