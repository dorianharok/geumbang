package com.wanted.resourceserver.exception

enum class ErrorCode(val defaultMessage: String) {
    EXAMPLE_CODE("example error"),
    ENTITY_NOT_FOUND("요청하신 자원을 찾을 수 없습니다."),
    PRICE_MISMATCH("현재 금 가격과 다릅니다."),
    GOLD_TYPE_MISMATCH("요청 품목과 다릅니다."),
    INVALID_ORDER_STATUS_CHANGE("주문 상태를 변경할 수 없습니다.")
}