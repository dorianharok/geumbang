package com.wanted.authserver.api.v1.request

import com.wanted.authserver.domain.User
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty

data class UserCreateRequest(
    @Schema(description = "유저 이름 [4~8 자리 영소문자, 숫자]", example = "username")
    @field:NotEmpty(message = "유저 이름은 필수입니다.")
    val username: String?,
    @Schema(description = "비밀번호 [8~20자리 영소문자, 숫자 필수포함]", example = "test1234")
    @field:NotEmpty(message = "비밀번호는 필수입니다.")
    val password: String?,
) {
    fun toUser(): User {
        return User(
            username = username!!,
            password = password!!,
        )
    }
}