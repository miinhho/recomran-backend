package io.miinhho.recomran.user.exception

import io.miinhho.recomran.common.response.APIResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class UserExceptionHandler {
    @ExceptionHandler(UserNotFoundException::class)
    fun userNotFoundExceptionHandler(ex: UserNotFoundException): APIResponseEntity {
        return ex.toResponseEntity()
    }
}