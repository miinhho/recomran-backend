package io.miinhho.recomran.auth.exception

import io.miinhho.recomran.common.exception.APIStatusException
import io.miinhho.recomran.common.response.APIStatusCode

open class AuthException(
    override val status: APIStatusCode
) : APIStatusException(status = status)

class InvalidTokenException()
    : AuthException(status = APIStatusCode.INVALID_TOKEN)

class IllegalEmailException()
    : AuthException(status = APIStatusCode.ILLEGAL_EMAIL)

class IllegalPasswordException()
    : AuthException(status = APIStatusCode.ILLEGAL_PASSWORD)

class ConflictEmailException()
    : AuthException(status = APIStatusCode.EMAIL_ALREADY_USE)