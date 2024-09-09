package com.wanted.resourceserver.exception

import org.springframework.http.HttpStatus

class GoldTypeMismatchException(
    val extra: String
): CoreException(HttpStatus.BAD_REQUEST, ErrorCode.GOLD_TYPE_MISMATCH) {
    override val message: String
        get() = super.message + extra
}