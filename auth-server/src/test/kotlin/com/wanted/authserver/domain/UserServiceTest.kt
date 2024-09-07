package com.wanted.authserver.domain

import com.wanted.authserver.exception.DuplicateUsernameException
import com.wanted.authserver.fixture.UserFixture
import com.wanted.authserver.storage.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

class UserServiceTest: ContainerTest() {

    @AfterEach
    fun tearDown() {
        userRepository.deleteAllInBatch()
    }

    @Test
    fun `회원가입시 유저가 DB에 저장된다`() {
        // given
        val initUser = UserFixture.initUser()

        // when
        val id = userService.join(initUser)

        // then
        assertThat(userRepository.findById(id)).isNotNull()
        assertThat(id).isNotEqualTo(0)
    }

    @Test
    fun `회원가입시 유저의 비밀번호가 암호화된다`() {
        // given
        val initUser = UserFixture.initUser()
        val password = initUser.password

        // when
        val id = userService.join(initUser)

        // then
        assertThat(userRepository.findByIdOrNull(id)?.password).isNotEqualTo(password)
    }

    @Test
    fun `회원가입시 유저이름이 중복되었을 경우 회원가입 할 수 없다`() {
        // given
        val initUser = UserFixture.initUser()
        userRepository.save(initUser)

        // when & then
        assertThrows<DuplicateUsernameException> {
            userService.join(initUser)
        }
    }

}