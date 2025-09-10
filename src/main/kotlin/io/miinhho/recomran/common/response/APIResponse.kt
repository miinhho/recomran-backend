package io.miinhho.recomran.common.response

import org.springframework.http.ResponseEntity

data class CommonResponse(
    val code: String,
    val message: String? = null,
    val data: Any? = null
) {
    companion object {
        fun from(apiResponse: APIResponse): CommonResponse {
            return CommonResponse(
                code = apiResponse.statusCode.code,
                message = apiResponse.message,
                data = apiResponse.data
            )
        }
    }
}

typealias APIResponseEntity = ResponseEntity<CommonResponse>

data class APIResponse(
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
                statusCode = statusCode,
                message = message ?: statusCode.defaultMessage,
                data = data
            ).toResponseEntity()

        fun fail(
            statusCode: APIStatusCode,
            message: String? = null
        ): APIResponseEntity =
            APIResponse(
                statusCode = statusCode,
                message = message ?: statusCode.defaultMessage,
            ).toResponseEntity()

        /**
         * `ResponseEntity` 의 `body` 로 사용될 `APIResponse` 를 반환합니다.
         *
         * @param statusCode - (기본값: `APIStatusCode.SUCCESS`)
         */
        fun body(
            statusCode: APIStatusCode = APIStatusCode.SUCCESS,
            data: Any? = null,
            message: String? = null,
        ): APIResponse =
            APIResponse(
                statusCode = statusCode,
                data = data,
                message = message
            )
    }

    private fun toResponseEntity(): APIResponseEntity {
        val commonResponse = CommonResponse.from(this)
        return ResponseEntity
            .status(statusCode.httpStatus)
            .body(commonResponse)
    }
}