package com.wanted.authserver.security

import com.wanted.authserver.support.TokenExpiredException
import com.wanted.authserver.support.TokenInvalidException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import java.util.*
import javax.crypto.SecretKey

class JwtProvider(
    private val secretString: String,
    private val accessTokenValidityInMilliseconds: Long,
    private val refreshTokenValidityInMilliseconds: Long
) {
    private val secretKey: SecretKey

    companion object {
        const val USER_ID = "userId"
        const val AUTHORIZATION = "Authorization"
        const val TOKEN_PREFIX = "Bearer "
    }

    init {
        val keyBytes = Decoders.BASE64.decode(secretString)
        secretKey = Keys.hmacShaKeyFor(keyBytes)
    }

    fun createAccessToken(id: Long): String {
        return createToken(id, accessTokenValidityInMilliseconds)
    }

    fun createRefreshToken(id: Long): String {
        return createToken(id, refreshTokenValidityInMilliseconds)
    }

    private fun createToken(id: Long, validityInMilliseconds: Number): String {
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds.toLong())

        return Jwts.builder()
            .claim(USER_ID, id)
            .issuedAt(now)
            .expiration(validity)
            .signWith(secretKey)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
            true
        } catch (e: ExpiredJwtException) {
            throw TokenExpiredException()
        } catch (e: JwtException) {
            throw TokenInvalidException()
        }
    }

    fun getIdFromToken(token: String): Long {
        val claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload

        return claims["id"]?.toString()?.toLongOrNull()
            ?: throw TokenInvalidException()
    }

    fun extractToken(request: HttpServletRequest): String? {
        request.getHeader(AUTHORIZATION)?.let {
            if (it.startsWith(TOKEN_PREFIX)) {
                return it.removePrefix(TOKEN_PREFIX)
            }
        }

        return null
    }
}