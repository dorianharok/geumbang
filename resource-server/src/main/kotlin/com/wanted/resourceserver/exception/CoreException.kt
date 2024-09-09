package com.wanted.resourceserver.exception

import org.springframework.http.HttpStatus

open class CoreException(
    val httpStatus: HttpStatus,
    errorCode: ErrorCode
) : RuntimeException(errorCode.defaultMessage) {
    override val message: String = errorCode.defaultMessage
}