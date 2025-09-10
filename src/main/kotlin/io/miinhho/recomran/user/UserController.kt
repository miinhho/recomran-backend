package io.miinhho.recomran.user

import io.miinhho.recomran.common.response.APIResponse
import io.miinhho.recomran.common.response.APIResponseEntity
import io.miinhho.recomran.common.response.APIStatusCode
import io.miinhho.recomran.user.dto.ChangeUserEmailRequest
import io.miinhho.recomran.user.dto.ChangeUserNameRequest
import io.miinhho.recomran.user.dto.ChangeUserPasswordRequest
import io.miinhho.recomran.user.model.User
import io.miinhho.recomran.user.service.UserService
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {
    @PatchMapping("/password")
    fun changeUserPassword(
        @Valid @RequestBody body: ChangeUserPasswordRequest,
        @AuthenticationPrincipal user: User,
    ): APIResponseEntity {
        userService.changePassword(user, body.newPassword)
        return APIResponse.success(statusCode = APIStatusCode.NO_CONTENT)
    }

    @PatchMapping("/email")
    fun changeUserEmail(
        @Valid @RequestBody body: ChangeUserEmailRequest,
        @AuthenticationPrincipal user: User,
    ): APIResponseEntity {
        userService.changeEmail(user, body.newEmail)
        return APIResponse.success(statusCode = APIStatusCode.NO_CONTENT)
    }

    @PatchMapping("/name")
    fun changeUserName(
        @Valid @RequestBody body: ChangeUserNameRequest,
        @AuthenticationPrincipal user: User,
    ): APIResponseEntity {
        userService.changeUsername(user, body.newUsername)
        return APIResponse.success(statusCode = APIStatusCode.NO_CONTENT)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(
        @RequestParam id: Long,
        @AuthenticationPrincipal user: User,
    ): APIResponseEntity {
        userService.deleteUser(requester = user, targetId = id)
        return APIResponse.success(statusCode = APIStatusCode.NO_CONTENT)
    }
}