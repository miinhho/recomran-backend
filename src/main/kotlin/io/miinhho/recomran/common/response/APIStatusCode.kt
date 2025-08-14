package io.miinhho.recomran.common.response

enum class APIStatusCode(val code: String) {
    API_SERVICE_DOWN("AD10"),
    INVALID_TOKEN("AU01"),
    INVALID_EMAIL("AU02"),
    INVALID_PASSWORD("AU03"),
    EMAIL_ALREADY_USE("AU04"),
}