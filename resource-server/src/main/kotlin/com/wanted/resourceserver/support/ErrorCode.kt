package com.wanted.resourceserver.support

enum class ErrorCode(val defaultMessage: String) {
    EXAMPLE_CODE("example error"),
    ENTITY_NOT_FOUND("요청하신 자원을 찾을 수 없습니다."),
    PRICE_MISMATCH("현재 금 가격과 다릅니다."),
    GOLD_TYPE_MISMATCH("요청 품목과 다릅니다."),
}