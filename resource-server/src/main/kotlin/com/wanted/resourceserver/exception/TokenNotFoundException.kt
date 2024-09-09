package com.wanted.resourceserver.exception

import org.springframework.http.HttpStatus

class TokenNotFoundException: CoreException(HttpStatus.UNAUTHORIZED, ErrorCode.NO_TOKEN)