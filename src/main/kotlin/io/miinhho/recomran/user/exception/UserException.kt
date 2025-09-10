package io.miinhho.recomran.user.exception

import io.miinhho.recomran.common.exception.APIStatusException
import io.miinhho.recomran.common.response.APIStatusCode

open class UserException(
    override val status: APIStatusCode,
    override val message: String? = null
) : APIStatusException(status = status, message = message)

class UserNotFoundException(userId: Long) : UserException(
    status = APIStatusCode.USER_NOT_FOUND,
    message = "유저를 찾을 수 없습니다: $userId"
)

class UserAccessDeniedException() : UserException(
    status = APIStatusCode.USER_ACCESS_DENIED,
)