package com.wanted.authserver.support

import org.springframework.http.HttpStatus

class TokenInvalidException: AuthException(HttpStatus.UNAUTHORIZED, AuthErrorCode.INVALID_TOKEN) {
}