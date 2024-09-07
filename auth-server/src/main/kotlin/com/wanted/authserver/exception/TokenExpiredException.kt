package com.wanted.authserver.exception

import org.springframework.http.HttpStatus

class TokenExpiredException: AuthException(HttpStatus.UNAUTHORIZED, AuthErrorCode.EXPIRED_TOKEN) {
}