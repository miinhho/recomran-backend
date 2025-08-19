package io.miinhho.recomran.user.model

@JvmInline
value class Email(val value: String) {
    init {
        require(value.isNotBlank()) { "이메일은 비어있을 수 없습니다" }
        require(Regex("^[^@\\n]+@[^@\\n]+\\.[^@\\n]+$")
            .matches(value)) { "올바른 이메일 형식이 아닙니다" }
    }
}

@JvmInline
value class Username(val value: String) {
    init {
        require(value.isNotBlank()) { "사용자명은 비어있을 수 없습니다" }
        require(value.length <= 50) { "사용자명은 50자를 초과할 수 없습니다" }
    }
}

enum class UserRole {
    USER, ADMIN
}