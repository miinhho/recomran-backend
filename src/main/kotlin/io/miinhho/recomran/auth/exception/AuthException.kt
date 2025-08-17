package io.miinhho.recomran.auth.exception

import io.miinhho.recomran.common.exception.APIStatusException
import io.miinhho.recomran.common.response.APIStatusCode

open class AuthException(
    override val message: String? = null,
    override val status: APIStatusCode
) : APIStatusException(message, status)

class InvalidTokenException(
    message: String? = null,
    status: APIStatusCode = APIStatusCode.INVALID_TOKEN
) : AuthException(message, status)

class InvalidEmailException(
    message: String? = null,
    status: APIStatusCode = APIStatusCode.INVALID_EMAIL
) : AuthException(message, status)

class InvalidPasswordException(
    message: String? = null,
    status: APIStatusCode = APIStatusCode.INVALID_PASSWORD
) : AuthException(message, status)

class ConflictEmailException(
    message: String? = null,
    status: APIStatusCode = APIStatusCode.EMAIL_ALREADY_USE
) : AuthException(message, status)