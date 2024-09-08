package com.wanted.authserver.domain

import com.wanted.authserver.exception.DuplicateUsernameException
import com.wanted.authserver.exception.InvalidPasswordException
import com.wanted.authserver.exception.UserNotFoundException
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

    @Test
    fun `유저이름과 비밀번호가 일치하는 경우 로그인한다`() {
        // given
        val initUser = UserFixture.initUser()
        val username = initUser.username
        val password = initUser.password
        userService.join(initUser)

        // when
        val token = userService.login(username, password)

        // then
        assertThat(token).isNotNull
    }

    @Test
    fun `유저이름과 비밀번호가 일치하지 않는 경우 로그인 할 수 없다`() {
        // given
        val initUser = UserFixture.initUser()
        val username = initUser.username
        val wrongPassword = "wrongPassword"
        userService.join(initUser)

        // when & then
        assertThrows<InvalidPasswordException> {
            userService.login(username, wrongPassword)
        }
    }

    @Test
    fun `유저가 존재하지않는 경우 로그인 할 수 없다`() {
        // given
        val wrongUsername = "wrongUsername"
        val wrongPassword = "wrongPassword"

        // when & then
        assertThrows<UserNotFoundException> {
            userService.login(wrongUsername, wrongPassword)
        }
    }

    @Test
    fun `로그인 시 AccessToken과 RefreshToken이 발급된다`() {
        // given
        val initUser = UserFixture.initUser()
        val username = initUser.username
        val password = initUser.password
        userService.join(initUser)

        // when
        val (accessToken, refreshToken) = userService.login(username, password)

        // then
        assertThat(accessToken).isNotNull
        assertThat(refreshToken).isNotNull
    }

    @Test
    fun `로그인 시 유저의 RefreshToken이 업데이트된다`() {
        // given
        val initUser = UserFixture.initUser()
        val username = initUser.username
        val password = initUser.password
        val id = userService.join(initUser)

        // when
        val (_, refreshToken) = userService.login(username, password)

        // then
        val user = userRepository.findById(id).get()
        assertThat(user.refreshToken).isEqualTo(refreshToken)
    }
}