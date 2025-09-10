package io.miinhho.recomran.user.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class ChangeUserPasswordRequest(
    @field:NotEmpty
    @field:Size(min = 6, max = 30, message = "비밀번호는 6자 이상, 30자 미만이여야 합니다")
    val newPassword: String,
)

data class ChangeUserEmailRequest(
    @field:NotEmpty
    @field:Email("유효하지 않은 이메일")
    val newEmail: String
)

data class ChangeUserNameRequest(
    @field:NotEmpty
    @field:Size(min = 3, max = 50, message = "사용자명은 3자 이상, 50자 이하여야 합니다")
    val newUsername: String
)