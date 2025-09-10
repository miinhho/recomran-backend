package io.miinhho.recomran.user.model

import io.miinhho.recomran.common.exception.APIStatusException
import io.miinhho.recomran.common.response.APIStatusCode

@JvmInline
value class Email(val value: String) {
    init {
        require(value.isNotBlank()) { "이메일은 비어있을 수 없습니다" }
        require(Regex("^[^@\\n]+@[^@\\n]+\\.[^@\\n]+$")
            .matches(value)) { "올바른 이메일 형식이 아닙니다" }
    }

    companion object {
        fun from(value: String): Email {
            try {
                return Email(value)
            } catch (e: IllegalArgumentException) {
                throw InvalidEmailException(e)
            }
        }
    }
}

class InvalidEmailException(e: IllegalArgumentException) : APIStatusException(
    status = APIStatusCode.INVALID_EMAIL,
    message = e.message
)

@JvmInline
value class Username(val value: String) {
    init {
        require(value.isNotBlank()) { "사용자명은 비어있을 수 없습니다" }
        require(value.length !in 3..50) { "사용자명은 3자 이상, 50자 이하여야 합니다" }
    }

    companion object {
        fun from(value: String): Username {
            try {
                return Username(value)
            } catch (e: IllegalArgumentException) {
                throw InvalidUsernameException(e)
            }
        }
    }
}

class InvalidUsernameException(e: IllegalArgumentException): APIStatusException(
    status = APIStatusCode.INVALID_USERNAME,
    message = e.message
)

@JvmInline
value class Password(val value: String) {
    init {
        require(value.isNotBlank()) { "비밀번호는 비어있을 수 없습니다" }
        require(value.length !in 6..30) { "비밀번호는 6자 이상, 30자 이하여야 합니다" }
    }

    companion object {
        fun from(value: String): Password {
            try {
                return Password(value)
            } catch (e: IllegalArgumentException) {
                throw InvalidPasswordException(e)
            }
        }
    }
}

class InvalidPasswordException(e: IllegalArgumentException) : APIStatusException(
    status = APIStatusCode.INVALID_PASSWORD,
    message = e.message
)

enum class UserRole {
    USER, ADMIN
}