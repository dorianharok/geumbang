package com.wanted.authserver.support

enum class AuthErrorCode(val defaultMessage: String) {
    INVALID_CREDENTIALS("잘못된 인증 정보입니다."),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    EXPIRED_TOKEN("토큰이 만료되었습니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EMAIL_ALREADY_EXISTS("이미 사용 중인 이메일입니다."),
    INVALID_PASSWORD("잘못된 비밀번호입니다."),
    INVALID_VERIFICATION_CODE("인증 코드가 올바르지 않습니다."),
    AUTHENTICATION_FAILED("인증에 실패했습니다.")
}