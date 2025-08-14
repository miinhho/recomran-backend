package io.miinhho.recomran.auth.token

import io.miinhho.recomran.security.jwt.JwtService
import org.springframework.http.ResponseCookie

class TokenUtil {
    companion object {
        fun getRefreshCookie(token: String): ResponseCookie =
            ResponseCookie.from("refreshToken", token)
                .httpOnly(true)
                .path("/")
                .maxAge(JwtService.REFRESH_TOKEN_VALID_MS)
                .build()
    }
}