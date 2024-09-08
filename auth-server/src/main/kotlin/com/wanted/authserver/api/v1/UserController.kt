package com.wanted.authserver.api.v1

import com.wanted.authserver.api.v1.request.UserCreateRequest
import com.wanted.authserver.api.v1.request.UserLoginRequest
import com.wanted.authserver.api.v1.response.DefaultIdResponse
import com.wanted.authserver.api.v1.response.TokenResponse
import com.wanted.authserver.domain.UserService
import com.wanted.common.ApiResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
):UserApi {

    @PostMapping("/api/v1/users")
    override fun join(@Valid @RequestBody request: UserCreateRequest): ApiResponse<DefaultIdResponse> {
        val id = userService.join(request.toUser())

        return ApiResponse.success(DefaultIdResponse(id))
    }

    @PostMapping("/api/v1/users/login")
    override fun login(@RequestBody request: UserLoginRequest): ApiResponse<TokenResponse> {
        val tokenPair = userService.login(request.username, request.password)

        return ApiResponse.success(TokenResponse(tokenPair))
    }
}