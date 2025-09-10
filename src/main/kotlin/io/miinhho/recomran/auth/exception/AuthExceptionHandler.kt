package io.miinhho.recomran.auth.exception

import io.miinhho.recomran.common.response.APIResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AuthExceptionHandler {
    @ExceptionHandler(AuthException::class)
    fun handleAuthException(ex: AuthException): APIResponseEntity {
        return ex.toResponseEntity()
    }
}