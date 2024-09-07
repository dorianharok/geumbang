package com.wanted.authserver.exception

import org.springframework.http.HttpStatus

class DuplicateUsernameException: AuthException(HttpStatus.CONFLICT, AuthErrorCode.USERNAME_ALREADY_EXISTS) {
}