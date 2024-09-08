package com.wanted.authserver.domain

import com.wanted.authserver.storage.UserRepository
import org.springframework.stereotype.Component

@Component
class UserAppender(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun append(user: User): User {
        val encodedPassword = passwordEncoder.encode(user.password)
        user.changePassword(encodedPassword)
        return userRepository.save(user)
    }
}