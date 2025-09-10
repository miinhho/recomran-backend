package io.miinhho.recomran.saved.exception

import io.miinhho.recomran.common.response.APIResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class SavedPlaceExceptionHandler {
    @ExceptionHandler(SavedPlaceException::class)
    fun handleSavedPlaceException(e: SavedPlaceException): APIResponseEntity {
        return e.toResponseEntity()
    }
}