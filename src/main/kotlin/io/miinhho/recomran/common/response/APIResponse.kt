package io.miinhho.recomran.common.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

typealias APIResponseEntity = ResponseEntity<APIResponse>

data class APIResponse(
    val success: Boolean,
    val statusCode: APIStatusCode?,
    val message: String?,
    val data: Any?
) {
    companion object {
        fun success(): APIResponse =
            APIResponse(
                success = true,
                statusCode = null,
                message = null,
                data = null,
            )

        fun success(data: Any?): APIResponse =
            APIResponse(
                success = true,
                statusCode = null,
                message = null,
                data,
            )

        fun fail(statusCode: APIStatusCode?): APIResponse =
            APIResponse(
                success = false,
                statusCode = statusCode,
                message = null,
                data = null,
            )

        fun fail(message: String?): APIResponse =
            APIResponse(
                success = false,
                statusCode = null,
                message = message,
                data = null,
            )

        fun fail(statusCode: APIStatusCode?, message: String?): APIResponse =
            APIResponse(
                success = false,
                statusCode = statusCode,
                message = message,
                data = null,
            )
    }

    private fun toResponseEntity(httpStatus: HttpStatus): APIResponseEntity =
        ResponseEntity.status(httpStatus).body(this)

    fun ok(): APIResponseEntity = toResponseEntity(HttpStatus.OK)

    fun status(httpStatus: HttpStatus): APIResponseEntity = toResponseEntity(httpStatus)
}