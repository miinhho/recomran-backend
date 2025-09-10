package io.miinhho.recomran.common.exception

import io.miinhho.recomran.common.response.APIResponseEntity
import io.miinhho.recomran.user.model.InvalidEmailException
import io.miinhho.recomran.user.model.InvalidPasswordException
import io.miinhho.recomran.user.model.InvalidUsernameException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(InvalidEmailException::class)
    fun handleInvalidEmailException(e: InvalidEmailException): APIResponseEntity {
        return e.toResponseEntity()
    }

    @ExceptionHandler(InvalidUsernameException::class)
    fun handleInvalidUsernameException(e: InvalidUsernameException): APIResponseEntity {
        return e.toResponseEntity()
    }

    @ExceptionHandler(InvalidPasswordException::class)
    fun handleInvalidPasswordException(e: InvalidPasswordException): APIResponseEntity {
        return e.toResponseEntity()
    }
}