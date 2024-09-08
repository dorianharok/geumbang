package com.wanted.authserver.domain

import com.wanted.authserver.exception.InvalidPasswordException
import com.wanted.authserver.exception.UserNotFoundException
import com.wanted.authserver.fixture.UserFixture
import com.wanted.authserver.security.JwtProvider
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UserLoginProcessorTest {

    private val userReader = mockk<UserReader>()
    private val tokenIssuer = mockk<TokenIssuer>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val userLoginProcessor = UserLoginProcessor(userReader, tokenIssuer, passwordEncoder)

    @Test
    fun `유저이름과 비밀번호가 일치하는 경우 로그인한다`() {
        // given
        val user = UserFixture.user()
        val username = user.username
        val password = user.password
        every { userReader.read(any()) } returns user
        every { passwordEncoder.matches(any(), any()) } returns true
        every { tokenIssuer.issueTokenPair(any()) } returns TokenPair("access token", "refresh token")

        // when
        val (accessToken, refreshToken) = userLoginProcessor.login(username, password)

        // then
        assertThat(accessToken).isEqualTo("access token")
        assertThat(refreshToken).isEqualTo("refresh token")
    }

    @Test
    fun `유저이름과 비밀번호가 일치하지 않는 경우 로그인 할 수 없다`() {
        // given
        val user = UserFixture.user()
        val username = user.username
        val password = user.password
        every { userReader.read(any()) } returns user
        every { passwordEncoder.matches(any(), any()) } returns false

        // when & then
        assertThrows<InvalidPasswordException> {
            userLoginProcessor.login(username, password)
        }
    }

    @Test
    fun `유저가 존재하지않는 경우 로그인 할 수 없다`() {
        // given
        val username = "wrong username"
        val password = "wrong password"
        every { userReader.read(any()) } throws UserNotFoundException()

        // when & then
        assertThrows<UserNotFoundException> {
            userLoginProcessor.login(username, password)
        }
    }

    @Test
    fun `로그인 시 AccessToken과 RefreshToken이 발급된다`() {
        val user = UserFixture.user()
        val username = user.username
        val password = user.password
        every { userReader.read(any()) } returns user
        every { passwordEncoder.matches(any(), any()) } returns true
        every { tokenIssuer.issueTokenPair(any()) } returns TokenPair("access token", "refresh token")

        // when
        val (accessToken, refreshToken) = userLoginProcessor.login(username, password)

        // then
        assertThat(accessToken).isEqualTo("access token")
        assertThat(refreshToken).isEqualTo("refresh token")
    }

    @Test
    fun `로그인 시 유저의 RefreshToken이 업데이트된다`() {
        val user = UserFixture.user()
        val username = user.username
        val password = user.password
        every { userReader.read(any()) } returns user
        every { passwordEncoder.matches(any(), any()) } returns true
        every { tokenIssuer.issueTokenPair(any()) } returns TokenPair("access token", "refresh token")

        // when
        val (_, refreshToken) = userLoginProcessor.login(username, password)

        // then
        assertThat(user.refreshToken).isEqualTo(refreshToken)
    }
}