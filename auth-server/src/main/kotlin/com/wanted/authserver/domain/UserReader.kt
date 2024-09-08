package com.wanted.authserver.domain

import com.wanted.authserver.exception.UserNotFoundException
import com.wanted.authserver.storage.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository
) {

    fun read(username: String): User {
        return userRepository.findByUsername(username) ?: throw UserNotFoundException()
    }

    fun readById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException()
    }
}