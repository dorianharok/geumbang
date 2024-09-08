package com.wanted.authserver.domain

import com.wanted.authserver.exception.TokenExpiredException
import com.wanted.authserver.exception.TokenInvalidException
import com.wanted.authserver.fixture.UserFixture
import com.wanted.authserver.jwt.JwtProvider
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class TokenIssuerTest {

    private val jwtProvider = mockk<JwtProvider>()
    private val userReader = mockk<UserReader>()
    private val tokenIssuer = TokenIssuer(jwtProvider, userReader)

    @Test
    fun `TokenPair를 발행한다`() {
        // given
        val id = 1L
        every { jwtProvider.createAccessToken(any()) } returns "access token"
        every { jwtProvider.createRefreshToken(any()) } returns "refresh token"

        // when
        val (accessToken, refreshToken) = tokenIssuer.issueTokenPair(id)

        // then
        assertThat(accessToken).isEqualTo("access token")
        assertThat(refreshToken).isEqualTo("refresh token")
    }

    @Test
    fun `RefreshToken이 검증될 경우 accessToken을 재발행한다`() {
        // given
        val refreshToken = "refreshToken"
        every { jwtProvider.removePrefix(any()) } returns refreshToken
        every { jwtProvider.getIdFromToken(any()) } returns 1L
        every { userReader.readById(any()) } returns UserFixture.user()
        every { jwtProvider.validateToken(any()) } returns true
        every { jwtProvider.createAccessToken(any()) } returns "access token"

        // when
        val (accessToken, _) = tokenIssuer.reIssue("Bearer refreshToken")

        // then
        assertThat(accessToken).isEqualTo("access token")
    }

    @Test
    fun `RefreshToken이 만료된 경우 accessToken을 재발급 할 수 없다`() {
        // given
        val refreshToken = "refresh token"
        every { jwtProvider.removePrefix(any()) } returns refreshToken
        every { jwtProvider.getIdFromToken(any()) } returns 1L
        every { userReader.readById(any()) } returns UserFixture.user()
        every { jwtProvider.validateToken(any()) } throws TokenExpiredException()

        // when & then
        assertThrows<TokenExpiredException> {
            tokenIssuer.reIssue(refreshToken)
        }
    }

    @Test
    fun `RefreshToken이 검증되지 않는 경우 accessToken을 재발급 할 수 없다`() {
        // given
        val refreshToken = "refresh token"
        every { jwtProvider.removePrefix(any()) } returns refreshToken
        every { jwtProvider.getIdFromToken(any()) } returns 1L
        every { userReader.readById(any()) } returns UserFixture.user()
        every { jwtProvider.validateToken(any()) } throws TokenInvalidException()

        // when & then
        assertThrows<TokenInvalidException> {
            tokenIssuer.reIssue(refreshToken)
        }
    }

    @Test
    fun `유저의 RefreshToken과 다른 경우 accessToken을 재발급 할 수 없다`() {
        // given
        val refreshToken = "refresh token"
        every { jwtProvider.removePrefix(any()) } returns refreshToken
        every { jwtProvider.getIdFromToken(any()) } returns 1L
        every { userReader.readById(any()) } returns UserFixture.user()
        every { jwtProvider.validateToken(any()) } returns true

        // when & then
        assertThrows<TokenInvalidException> {
            tokenIssuer.reIssue(refreshToken)
        }
    }
}