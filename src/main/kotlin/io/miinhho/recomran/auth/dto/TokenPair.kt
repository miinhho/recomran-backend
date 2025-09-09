package io.miinhho.recomran.auth.dto

import io.miinhho.recomran.auth.token.TokenUtil
import org.springframework.http.ResponseCookie

data class TokenPair(
    val accessToken: String,
    val refreshToken: String,
) {
    fun toRefreshCookie(): ResponseCookie =
        TokenUtil.getRefreshCookie(this.refreshToken)
}