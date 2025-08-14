package io.miinhho.recomran.auth.exception

import io.miinhho.recomran.common.response.APIResponse
import io.miinhho.recomran.common.response.APIResponseEntity
import io.miinhho.recomran.common.response.APIStatusCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class AuthExceptionHandler {
    @ExceptionHandler(InvalidTokenException::class)
    fun invalidTokenException(): APIResponseEntity {
        return APIResponse
            .fail(APIStatusCode.INVALID_TOKEN)
            .status(HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(InvalidEmailException::class)
    fun invalidEmailException(): APIResponseEntity {
        return APIResponse
            .fail(APIStatusCode.INVALID_EMAIL)
            .status(HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidPasswordException::class)
    fun invalidPasswordException(): APIResponseEntity {
        return APIResponse
            .fail(APIStatusCode.INVALID_PASSWORD)
            .status(HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ConflictEmailException::class)
    fun conflictEmailException(): APIResponseEntity {
        return APIResponse
            .fail(APIStatusCode.EMAIL_ALREADY_USE)
            .status(HttpStatus.CONFLICT)
    }
}