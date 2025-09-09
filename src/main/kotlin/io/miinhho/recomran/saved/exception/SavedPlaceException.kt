package io.miinhho.recomran.saved.exception

import io.miinhho.recomran.common.exception.APIStatusException
import io.miinhho.recomran.common.response.APIStatusCode

open class SavedPlaceException(
    override val status: APIStatusCode,
    override val message: String? = null
) : APIStatusException(status = status, message = message)

class SavedPlaceNotFoundException() : SavedPlaceException(
    status = APIStatusCode.SAVED_PLACE_NOT_FOUND,
)

class SavedPlaceNotOwnerException() : SavedPlaceException(
    status = APIStatusCode.SAVED_PLACE_NOT_OWNER
)

class SavedPlaceInvalidNameException(e: IllegalArgumentException) : SavedPlaceException(
    status = APIStatusCode.INVALID_REQUEST,
    message = e.message
)