package com.wanted.resourceserver.support

import org.springframework.http.HttpStatus

class GoldTypeMismatchException: CoreException(HttpStatus.BAD_REQUEST, ErrorCode.GOLD_TYPE_MISMATCH) {
}