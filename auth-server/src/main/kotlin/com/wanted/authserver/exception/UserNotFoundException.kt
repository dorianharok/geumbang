package com.wanted.authserver.exception

import org.springframework.http.HttpStatus

class UserNotFoundException: AuthException(HttpStatus.BAD_REQUEST, AuthErrorCode.USER_NOT_FOUND) {
}