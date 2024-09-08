package com.wanted.authserver.exception

import org.springframework.http.HttpStatus

class InvalidPasswordException: AuthException(HttpStatus.UNAUTHORIZED, AuthErrorCode.INVALID_PASSWORD) {
}