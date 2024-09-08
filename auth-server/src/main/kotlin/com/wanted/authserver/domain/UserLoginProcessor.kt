package com.wanted.authserver.domain

import com.wanted.authserver.exception.InvalidPasswordException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserLoginProcessor(
    private val userReader: UserReader,
    private val tokenIssuer: TokenIssuer,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun login(username: String, password: String): TokenPair {
        val user = userReader.read(username)
        if(!passwordEncoder.matches(password, user.password)) {
            throw InvalidPasswordException()
        }

        val tokenPair = tokenIssuer.issueTokenPair(user.id)
        user.updateRefreshToken(tokenPair.refreshToken)

        return tokenPair
    }
}