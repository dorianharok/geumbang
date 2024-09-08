package com.wanted.authserver.api.v1

import com.wanted.authserver.api.v1.request.UserCreateRequest
import com.wanted.authserver.api.v1.request.UserLoginRequest
import com.wanted.authserver.api.v1.response.DefaultIdResponse
import com.wanted.authserver.api.v1.response.TokenResponse
import com.wanted.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "유저 API")
interface UserApi {
    @Operation(summary = "유저 회원가입 API")
    fun join(request: UserCreateRequest): ApiResponse<DefaultIdResponse>

    @Operation(summary = "유저 로그인 API")
    fun login(request: UserLoginRequest): ApiResponse<TokenResponse>

    @Operation(summary = "유저 토큰 재발행 API")
    fun reIssue(token: String?): ApiResponse<TokenResponse>
}