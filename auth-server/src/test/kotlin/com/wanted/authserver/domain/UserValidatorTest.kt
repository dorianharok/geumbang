package com.wanted.authserver.domain

import com.wanted.authserver.exception.DuplicateUsernameException
import com.wanted.authserver.storage.UserRepository
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UserValidatorTest {

    private val userRepository = mockk<UserRepository>()
    private val userValidator = UserValidator(userRepository)

    @Test
    fun `이미 존재하는 유저 아이디로 가입할 수 없다`() {
        // Given
        every { userRepository.existsByUsername(any()) } returns true
        val username = "test"

        // When & Then
        assertThrows<DuplicateUsernameException> {
            userValidator.validateUsername(username)
        }
    }
}