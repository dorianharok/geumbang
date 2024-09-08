package com.wanted.resourceserver.support

import org.springframework.http.HttpStatus

class PriceMismatchException: CoreException(HttpStatus.BAD_REQUEST, ErrorCode.PRICE_MISMATCH) {
}