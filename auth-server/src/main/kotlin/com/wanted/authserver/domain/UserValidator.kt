package com.wanted.authserver.domain

import com.wanted.authserver.exception.DuplicateUsernameException
import com.wanted.authserver.storage.UserRepository
import org.springframework.stereotype.Component

@Component
class UserValidator(
    private val userRepository: UserRepository
) {
    fun validateUsername(username: String) {
        if (userRepository.existsByUsername(username)) {
            throw DuplicateUsernameException()
        }
    }
}