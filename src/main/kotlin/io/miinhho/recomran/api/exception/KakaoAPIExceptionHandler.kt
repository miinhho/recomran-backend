package io.miinhho.recomran.api.exception

import io.miinhho.recomran.common.response.APIResponse
import io.miinhho.recomran.common.response.APIStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class KakaoAPIExceptionHandler {
    @ExceptionHandler(KakaoAPIException::class)
    fun kakaoAPIException(ex: KakaoAPIException): ResponseEntity<APIResponse> {
        val apiResponse = APIResponse(
            success = false,
            statusCode = APIStatusCode.API_SERVICE_DOWN,
            message = "Kakao API 서비스를 사용할 수 없습니다."
        )
        return ResponseEntity.internalServerError().body(apiResponse)
    }
}