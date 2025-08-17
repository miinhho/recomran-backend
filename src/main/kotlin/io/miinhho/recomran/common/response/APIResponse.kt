package io.miinhho.recomran.common.response

import org.springframework.http.ResponseEntity

typealias APIResponseEntity = ResponseEntity<APIResponse>

data class APIResponse(
    val success: Boolean,
    val statusCode: APIStatusCode,
    val message: String? = null,
    val data: Any? = null
) {
    companion object {
        fun success(
            statusCode: APIStatusCode = APIStatusCode.SUCCESS,
            data: Any? = null,
            message: String? = null
        ): APIResponseEntity =
            APIResponse(
                success = true,
                statusCode = statusCode,
                message = message ?: statusCode.defaultMessage,
                data = data
            ).toResponseEntity()

        fun fail(
            statusCode: APIStatusCode,
            message: String? = null
        ): APIResponseEntity =
            APIResponse(
                success = false,
                statusCode = statusCode,
                message = message ?: statusCode.defaultMessage,
            ).toResponseEntity()

        /**
         * ResponseEntity 의 body 로 사용될 APIResponse 를 반환합니다.
         *
         * @param success - (기본값: true)
         * @param statusCode - (기본값: APIStatusCode.SUCCESS)
         */
        fun body(
            success: Boolean = true,
            statusCode: APIStatusCode = APIStatusCode.SUCCESS,
            data: Any? = null,
            message: String? = null,
        ): APIResponse =
            APIResponse(
                success = success,
                statusCode = statusCode,
                data = data,
                message = message
            )
    }

    private fun toResponseEntity(): APIResponseEntity =
        ResponseEntity
            .status(statusCode.httpStatus)
            .body(this)
}