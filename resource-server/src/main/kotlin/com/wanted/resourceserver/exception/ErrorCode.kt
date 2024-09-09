package com.wanted.resourceserver.exception

enum class ErrorCode(val defaultMessage: String) {
    NO_TOKEN("JWT 토큰이 없습니다."),
    ENTITY_NOT_FOUND("요청하신 자원을 찾을 수 없습니다."),
    PRICE_MISMATCH("현재 금 가격과 다릅니다."),
    GOLD_TYPE_MISMATCH("요청 품목과 다릅니다."),
    INVALID_ORDER_STATUS_CHANGE("주문 상태를 변경할 수 없습니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다.")
}