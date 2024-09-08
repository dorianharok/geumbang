package com.wanted.resourceserver.api.v1

import com.wanted.common.ApiResponse
import com.wanted.resourceserver.api.v1.request.OrderCreateRequest
import com.wanted.resourceserver.api.v1.response.DefaultIdResponse
import com.wanted.resourceserver.security.LoginUser
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "주문 API")
interface OrderApi {
    @Operation(summary = "주문 생성 API")
    fun createOrder(@RequestBody request: OrderCreateRequest,
                    @AuthenticationPrincipal loginUser: LoginUser): ApiResponse<DefaultIdResponse>
}