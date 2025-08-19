package io.miinhho.recomran.api.exception

import io.miinhho.recomran.common.response.APIResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class KakaoAPIExceptionHandler {
    @ExceptionHandler(KakaoAPIException::class)
    fun kakaoAPIException(ex: KakaoAPIException): APIResponseEntity =
        ex.toResponseEntity()
}