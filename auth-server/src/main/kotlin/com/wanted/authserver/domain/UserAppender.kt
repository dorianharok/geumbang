package com.wanted.authserver.domain

import com.wanted.authserver.storage.UserRepository
import org.springframework.stereotype.Component

@Component
class UserAppender(
    private val userRepository: UserRepository
) {
    fun append(user: User): User {
        user.encodePassword()
        return userRepository.save(user)
    }
}