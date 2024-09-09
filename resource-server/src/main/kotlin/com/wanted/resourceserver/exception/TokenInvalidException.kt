package com.wanted.resourceserver.exception

import org.springframework.http.HttpStatus

class TokenInvalidException: CoreException(HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_TOKEN)