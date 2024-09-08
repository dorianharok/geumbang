package com.wanted.authserver.domain

import com.wanted.authserver.exception.TokenInvalidException
import com.wanted.authserver.jwt.JwtProvider
import org.springframework.stereotype.Component

@Component
class TokenIssuer(
    private val jwtProvider: JwtProvider,
    private val userReader: UserReader
) {
    fun issueTokenPair(id: Long): TokenPair {
        val accessToken = jwtProvider.createAccessToken(id)
        val refreshToken = jwtProvider.createRefreshToken(id)

        return TokenPair(accessToken, refreshToken)
    }

    fun reIssue(refreshToken: String?): TokenPair {
        val extractToken = jwtProvider.removePrefix(refreshToken)
        val id = jwtProvider.getIdFromToken(extractToken)
        jwtProvider.validateToken(extractToken)
        val user = userReader.readById(id)
        if(user.refreshToken != extractToken) {
            throw TokenInvalidException()
        }


        val newAccessToken = jwtProvider.createAccessToken(id)

        return TokenPair(newAccessToken, extractToken)
    }
}