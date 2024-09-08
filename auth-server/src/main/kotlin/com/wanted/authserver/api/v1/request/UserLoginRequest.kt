package com.wanted.authserver.api.v1.request

import io.swagger.v3.oas.annotations.media.Schema

data class UserLoginRequest(
    @Schema(description = "유저이름", example = "username")
    val username: String,
    @Schema(description = "비밀번호", example = "username")
    val password: String
)
