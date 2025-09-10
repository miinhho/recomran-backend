package io.miinhho.recomran.saved

import io.miinhho.recomran.common.response.APIResponse
import io.miinhho.recomran.common.response.APIResponseEntity
import io.miinhho.recomran.common.response.APIStatusCode
import io.miinhho.recomran.saved.dto.AddPlaceToSavedPlaceRequest
import io.miinhho.recomran.saved.dto.AddSavedPlaceRequest
import io.miinhho.recomran.saved.dto.DeletePlacesFromSavedPlaceRequest
import io.miinhho.recomran.saved.dto.DeleteSavedPlaceRequest
import io.miinhho.recomran.saved.dto.GetSavedPlaceContainsCertainPlaceRequest
import io.miinhho.recomran.saved.dto.UpdateSavedPlaceNameRequest
import io.miinhho.recomran.saved.service.SavedPlaceService
import io.miinhho.recomran.user.model.User
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val SAVED_PLACE_PAGE_SIZE: Int = 5
const val PLACE_PAGE_SIZE: Int = 10

@RestController
@RequestMapping("/api/saved-place")
class SavedPlaceController(private val savedPlaceService: SavedPlaceService) {
    @PostMapping("/add")
    fun addSavedPlace(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody body: AddSavedPlaceRequest
    ): APIResponseEntity {
        savedPlaceService.addSavedPlace(user.id!!, body.name)
        return APIResponse.success(statusCode = APIStatusCode.NO_CONTENT)
    }

    @DeleteMapping("/{id}")
    fun deleteSavedPlace(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody body: DeleteSavedPlaceRequest
    ): APIResponseEntity {
        savedPlaceService.deleteSavedPlace(user, body.id)
        return APIResponse.success(statusCode = APIStatusCode.NO_CONTENT)
    }

    @PostMapping("/{id}/add-place")
    fun addPlaceToSavedPlace(
        @PathVariable("id") id: Long,
        @RequestBody body: AddPlaceToSavedPlaceRequest
    ): APIResponseEntity {
        val places = body.places.map { it.toPlace() }
        savedPlaceService.addPlaces(id, places)
        return APIResponse.success(statusCode = APIStatusCode.NO_CONTENT)
    }

    @DeleteMapping("/{id}/remove-place")
    fun deletePlacesFromSavedPlace(
        @PathVariable("id") id: Long,
        @RequestBody body: DeletePlacesFromSavedPlaceRequest
    ): APIResponseEntity {
        savedPlaceService.deletePlaces(id, body.placeIds)
        return APIResponse.success(statusCode = APIStatusCode.NO_CONTENT)
    }

    @PatchMapping("/{id}/update-name")
    fun updateSavedPlaceName(
        @PathVariable("id") id: Long,
        @Valid @RequestBody body: UpdateSavedPlaceNameRequest
    ): APIResponseEntity {
        savedPlaceService.updateName(id, body.newName)
        return APIResponse.success(statusCode = APIStatusCode.NO_CONTENT)
    }

    @PostMapping("/search")
    fun getSavedPlaceContainsCertainPlace(
        @AuthenticationPrincipal user: User,
        @RequestBody body: GetSavedPlaceContainsCertainPlaceRequest
    ): APIResponseEntity {
        val savedPlaces = savedPlaceService.getSavedPlacesWithPlaceId(
            userId = user.id!!, placeId = body.placeId)
        return APIResponse.success(data = savedPlaces)
    }

    @GetMapping("/all")
    fun getAllSavedPlaces(
        @AuthenticationPrincipal user: User,
        @PageableDefault(size = SAVED_PLACE_PAGE_SIZE, page = 0) pageable: Pageable
    ): APIResponseEntity {
        val places = savedPlaceService.getSavedPlaces(
            userId = user.id!!, pageable = pageable)
        return APIResponse.success(data = places)
    }

    @GetMapping("/{id}/places")
    fun getPlacesFromSavedPlace(
        @AuthenticationPrincipal user: User,
        @PathVariable("id") id: Long,
        @PageableDefault(size = PLACE_PAGE_SIZE, page = 0) pageable: Pageable
    ): APIResponseEntity {
        val places = savedPlaceService.getPlaces(
            user = user, id = id, pageable = pageable)
        return APIResponse.success(data = places)
    }
}