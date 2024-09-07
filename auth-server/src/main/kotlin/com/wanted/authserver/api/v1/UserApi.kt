package com.wanted.authserver.api.v1

import com.wanted.authserver.api.v1.request.UserCreateRequest
import com.wanted.authserver.api.v1.response.DefaultIdResponse
import com.wanted.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "유저 API")
interface UserApi {
    @Operation(summary = "유저 회원가입 API")
    fun join(request: UserCreateRequest): ApiResponse<DefaultIdResponse>
}