package io.miinhho.recomran.auth

import io.miinhho.recomran.auth.dto.AuthRequest
import io.miinhho.recomran.auth.dto.RefreshRequest
import io.miinhho.recomran.common.response.APIResponse
import io.miinhho.recomran.common.response.APIResponseEntity
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(
        @Valid @RequestBody body: AuthRequest
    ): APIResponseEntity {
        val user = authService.register(body.email, body.password)
        return APIResponse.success(
            data = user
        )
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody body: AuthRequest
    ): APIResponseEntity {
        val tokenPair = authService.login(body.email, body.password)
        val refreshCookie = tokenPair.toRefreshCookie()
        val body = APIResponse.body(
            data = tokenPair.accessToken
        )

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            .body(body)
    }

    fun refresh(
        @RequestBody body: RefreshRequest
    ): APIResponseEntity {
        val tokenPair = authService.refresh(body.refreshToken)
        val refreshCookie = tokenPair.toRefreshCookie()
        val body = APIResponse.body(
            data = tokenPair.accessToken
        )

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            .body(body)
    }
}