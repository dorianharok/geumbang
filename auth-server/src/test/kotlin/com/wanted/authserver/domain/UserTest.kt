package com.wanted.authserver.domain

import com.wanted.authserver.exception.InvalidPasswordFormatException
import com.wanted.authserver.exception.InvalidUsernameException
import com.wanted.authserver.fixture.UserFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.assertThrows

class UserTest {

    @Test
    fun `유저 비밀번호를 암호화한다`() {
        val user = UserFixture.initUser()
        user.changePassword("encodedPassword")

        assertThat(user.password).isEqualTo("encodedPassword")
    }

    /**
     * 4 ~ 8자리로 구성된 영소문자, 숫자 조합
     */
    @Test
    fun `회원가입시 유저이름이 생성 조건에 맞지 않으면 가입할 수 없다`() {
        assertThrows<InvalidUsernameException> {
            User(
                username = "abc",
                password = "asdf1234",
                refreshToken = null,
                id = 0
            )
        }
    }

    /**
     * 8 ~ 20자리로 구성된 영소문자, 숫자 조합 (특수문자, 영대문자 선택적 가능)
     */
    @Test
    fun `회원가입시 비밀번호가 생성 조건에 맞지 않으면 가입할 수 없다`() {
        assertThrows<InvalidPasswordFormatException> {
            User(
                username = "abcf",
                password = "asdf",
                refreshToken = null,
                id = 0
            )
        }
    }
}