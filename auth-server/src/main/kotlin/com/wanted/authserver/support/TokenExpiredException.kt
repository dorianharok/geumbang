package com.wanted.authserver.support

import org.springframework.http.HttpStatus

class TokenExpiredException: AuthException(HttpStatus.UNAUTHORIZED, AuthErrorCode.EXPIRED_TOKEN) {
}