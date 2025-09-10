package io.miinhho.recomran.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class AuthRequest(
    @field:NotEmpty
    @field:Email(message = "유효하지 않은 이메일")
    val email: String,

    @field:NotEmpty
    @field:Size(min = 6, max = 30, message = "비밀번호는 6자 이상, 30자 미만이여야 합니다")
    val password: String,
)

data class RefreshRequest(
    @field:NotEmpty
    val refreshToken: String
)
