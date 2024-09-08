package com.wanted.authserver.domain

import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Component

interface PasswordEncoder {
    fun encode(rawPassword: String): String
    fun matches(rawPassword: String, encodedPassword: String): Boolean
}

@Component
class BcryptPasswordEncoder:PasswordEncoder {
    override fun encode(rawPassword: String): String {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt())
    }

    override fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return BCrypt.checkpw(rawPassword, encodedPassword)
    }
}