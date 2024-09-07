package com.wanted.authserver.exception

enum class AuthErrorCode(val defaultMessage: String) {
    INVALID_CREDENTIALS("잘못된 인증 정보입니다."),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    EXPIRED_TOKEN("토큰이 만료되었습니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    USERNAME_ALREADY_EXISTS("이미 사용 중인 이름입니다."),
    INVALID_PASSWORD("잘못된 비밀번호입니다."),
    INVALID_USER_NAME("사용자 이름은 영문 소문자와 숫자만 사용 가능하며, 4~8자여야 합니다."),
    INVALID_PASSWORD_FORMAT("비밀번호는 숫자와 영문 소문자로 구성된 8~20자여야 합니다."),
    INVALID_VERIFICATION_CODE("인증 코드가 올바르지 않습니다."),
    AUTHENTICATION_FAILED("인증에 실패했습니다.")
}