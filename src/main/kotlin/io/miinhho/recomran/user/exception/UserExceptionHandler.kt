package io.miinhho.recomran.user.exception

import io.miinhho.recomran.common.response.APIResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class UserExceptionHandler {
    @ExceptionHandler(UserException::class)
    fun userExceptionHandler(ex: UserException): APIResponseEntity {
        return ex.toResponseEntity()
    }
}