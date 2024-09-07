package com.wanted.authserver.exception

import org.springframework.http.HttpStatus

class InvalidPasswordFormatException: AuthException(HttpStatus.BAD_REQUEST, AuthErrorCode.INVALID_PASSWORD_FORMAT) {
}