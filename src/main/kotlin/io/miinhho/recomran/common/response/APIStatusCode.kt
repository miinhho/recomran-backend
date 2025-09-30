package io.miinhho.recomran.common.response

import org.springframework.http.HttpStatus

enum class APIStatusCode(
    val code: String,
    val httpStatus: HttpStatus,
    val defaultMessage: String? = null
) {
    SUCCESS("SC00", HttpStatus.OK),
    API_SERVICE_DOWN("AD10", HttpStatus.INTERNAL_SERVER_ERROR, "서비스가 일시적으로 중단되었습니다"),
}