package com.wanted.authserver.exception

import org.springframework.http.HttpStatus

class TokenInvalidException: AuthException(HttpStatus.UNAUTHORIZED, AuthErrorCode.INVALID_TOKEN) {
}