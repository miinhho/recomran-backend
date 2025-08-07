package io.miinhho.recomran.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern

data class AuthRequest(
    @Email(message = "유효하지 않은 이메일")
    val email: String,
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d).{8,}\\\$",
        message = "비밀번호는 최소 8자리여야 하며, 한 개 이상의 숫자와 대소문자를 포함해야 합니다."
    )
    val password: String,
)
