package io.miinhho.recomran.common.exception

import io.miinhho.recomran.common.response.APIResponse
import io.miinhho.recomran.common.response.APIResponseEntity
import io.miinhho.recomran.common.response.APIStatusCode

abstract class APIStatusException(
    override val message: String? = null,
    open val status: APIStatusCode
) : RuntimeException(message) {
    /**
     * APIResponse.fail 로 처리된 APIResponseEntity 를 반환합니다.
     *
     * APIStatusException 을 상속하는 객체는 success 로 처리되면 안 됩니다.
     */
    fun toResponseEntity(): APIResponseEntity =
        APIResponse.fail(
            statusCode = status,
            message = message ?: status.defaultMessage
        )
}