package com.wanted.resourceserver.exception

import org.springframework.http.HttpStatus

class OrderStatusException(
    private val extra: String
): CoreException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_ORDER_STATUS_CHANGE) {
    override val message: String
        get() = "${super.message} $extra"
}