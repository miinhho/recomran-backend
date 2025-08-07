package io.miinhho.recomran.auth

import io.miinhho.recomran.auth.dto.AuthRequest
import io.miinhho.recomran.auth.dto.RefreshRequest
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(
        @Valid @RequestBody body: AuthRequest
    ) {
        authService.register(body.email, body.password)
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody body: AuthRequest
    ): AuthService.TokenPair {
        return authService.login(body.email, body.password)
    }

    fun refresh(
        @RequestBody body: RefreshRequest
    ): AuthService.TokenPair {
        return authService.refresh(body.refreshToken)
    }
}