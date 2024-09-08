package com.wanted.authserver.jwt

import com.wanted.authserver.exception.TokenExpiredException
import com.wanted.authserver.exception.TokenInvalidException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @Value("\${security.jwt.secret}")
    private val secretString: String,
    @Value("\${security.jwt.expiration.access}")
    private val accessTokenValidityInMilliseconds: Long,
    @Value("\${security.jwt.expiration.refresh}")
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

        return claims[USER_ID]?.toString()?.toLongOrNull()
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

    fun removePrefix(token: String?): String {
        if(token == null || !token.startsWith(TOKEN_PREFIX)) {
            throw TokenInvalidException()
        }

        return token.removePrefix(TOKEN_PREFIX)
    }
}