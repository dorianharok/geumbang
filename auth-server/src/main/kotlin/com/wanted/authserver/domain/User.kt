package com.wanted.authserver.domain

import com.wanted.authserver.exception.InvalidPasswordFormatException
import com.wanted.authserver.exception.InvalidUsernameException
import jakarta.persistence.*
import java.util.regex.Pattern

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false, unique = true)
    val username: String,

    @Column(nullable = false)
    var password: String,

    var refreshToken: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
) {
    init {
        validateUsername()
        validatePassword()
    }

    fun encodePassword() {
        this.password = PasswordEncoder.encode(password)
    }

    private fun validateUsername() {
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw InvalidUsernameException()
        }
    }

    private fun validatePassword() {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw InvalidPasswordFormatException()
        }
    }

    companion object {
        private val USERNAME_PATTERN = Pattern.compile("^[a-z0-9]{4,8}$")
        private val PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*()_+\\-=]{8,20}$")
    }
}