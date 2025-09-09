package io.miinhho.recomran.common.response

import org.springframework.http.HttpStatus

enum class APIStatusCode(
    private val code: String,
    val httpStatus: HttpStatus,
    val defaultMessage: String? = null
) {
    SUCCESS("SC00", HttpStatus.OK),
    NO_CONTENT("SC01", HttpStatus.NO_CONTENT),

    // API Service codes
    API_SERVICE_DOWN("AD10", HttpStatus.INTERNAL_SERVER_ERROR, "서비스가 일시적으로 중단되었습니다"),

    INVALID_TOKEN("AU01", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다"),
    INVALID_EMAIL("AU02", HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다"),
    INVALID_PASSWORD("AU03", HttpStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다"),
    EMAIL_ALREADY_USE("AU04", HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다"),

    USER_NOT_FOUND("UR01", HttpStatus.NOT_FOUND),

    SAVED_PLACE_NOT_FOUND("SP01", HttpStatus.NOT_FOUND, "저장된 장소를 찾을 수 없습니다"),
    SAVED_PLACE_NOT_OWNER("SP02", HttpStatus.BAD_REQUEST, "저장된 장소를 찾을 수 없습니다"),

    INVALID_REQUEST("VL01", HttpStatus.BAD_REQUEST, "요청 데이터가 올바르지 않습니다");
}