package com.wanted.authserver.domain

import com.wanted.authserver.security.JwtProvider
import org.springframework.stereotype.Component

@Component
class TokenIssuer(
    private val jwtProvider: JwtProvider
) {
    fun issueTokenPair(id: Long): TokenPair {
        val accessToken = jwtProvider.createAccessToken(id)
        val refreshToken = jwtProvider.createRefreshToken(id)

        return TokenPair(accessToken, refreshToken)
    }
}