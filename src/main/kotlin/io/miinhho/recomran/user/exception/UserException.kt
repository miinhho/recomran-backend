package io.miinhho.recomran.user.exception

import io.miinhho.recomran.common.exception.APIStatusException
import io.miinhho.recomran.common.response.APIStatusCode

class UserNotFoundException(userId: Long) : APIStatusException(
    status = APIStatusCode.USER_NOT_FOUND,
    message = "유저를 찾을 수 없습니다: $userId"
)