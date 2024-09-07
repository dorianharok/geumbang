package com.wanted.authserver.storage

import com.wanted.authserver.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun existsByUsername(username: String): Boolean
}