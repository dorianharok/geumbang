package com.wanted.authserver.domain

import com.wanted.authserver.fixture.UserFixture
import com.wanted.authserver.security.JwtProvider
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class TokenIssuerTest {

    private val jwtProvider = mockk<JwtProvider>()
    private val tokenIssuer = TokenIssuer(jwtProvider)

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
}