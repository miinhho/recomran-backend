package io.miinhho.recomran.api.exception

import io.miinhho.recomran.common.response.APIResponse
import io.miinhho.recomran.common.response.APIResponseEntity
import io.miinhho.recomran.common.response.APIStatusCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class KakaoAPIExceptionHandler {
    @ExceptionHandler(KakaoAPIException::class)
    fun kakaoAPIException(ex: KakaoAPIException): APIResponseEntity {
        return APIResponse
            .fail(APIStatusCode.API_SERVICE_DOWN)
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
    }
}