package com.wanted.resourceserver.exception

import org.springframework.http.HttpStatus

class EntityNotFoundException: CoreException(HttpStatus.BAD_REQUEST, ErrorCode.ENTITY_NOT_FOUND) {
}