package com.wanted.authserver.fixture

import com.wanted.authserver.domain.User

object UserFixture {
    fun initUser() = User(
        username = "test",
        password = "test1234",
        refreshToken = null,
        id = 0
    )

    fun user() = User(
        username = "test",
        password = "encodedPassword123",
        refreshToken = "refreshToken",
        id = 1L
    )
}