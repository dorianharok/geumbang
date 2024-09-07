package com.wanted.authserver.exception

import org.springframework.http.HttpStatus

class InvalidUsernameException: AuthException(HttpStatus.BAD_REQUEST, AuthErrorCode.INVALID_USER_NAME) {
}