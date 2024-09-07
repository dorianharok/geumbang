package com.wanted.authserver.domain

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userValidator: UserValidator,
    private val userAppender: UserAppender
) {
    fun join(user: User): Long {
        userValidator.validateUsername(user.username)
        return userAppender.append(user).id
    }
}