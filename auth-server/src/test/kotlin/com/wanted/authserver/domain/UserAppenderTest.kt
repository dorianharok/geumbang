package com.wanted.authserver.domain

import com.wanted.authserver.fixture.UserFixture
import com.wanted.authserver.storage.UserRepository
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UserAppenderTest {

    private val userRepository = mockk<UserRepository>()
    private val userAppender = UserAppender(userRepository)

    @Test
    fun `회원가입시 유저 비밀번호는 암호화된다`() {
        // Given
        val initUser = UserFixture.initUser()
        every { userRepository.save(initUser) } returns UserFixture.user()

        // When
        val append = userAppender.append(initUser)

        // Then
        assertThat(append.password).isEqualTo("encodedPassword123")
    }

    @Test
    fun `유저 회원가입을 한다`() {
        // Given
        val initUser = UserFixture.initUser()
        every { userRepository.save(initUser) } returns UserFixture.user()

        // When
        val append = userAppender.append(initUser)

        // Then
        assertThat(append.id).isEqualTo(1L)
    }
}