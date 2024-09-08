package com.wanted.resourceserver.api.v1

import com.wanted.common.ApiResponse
import com.wanted.resourceserver.api.v1.request.OrderCreateRequest
import com.wanted.resourceserver.api.v1.response.DefaultIdResponse
import com.wanted.resourceserver.domain.order.OrderService
import com.wanted.resourceserver.security.LoginUser
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val orderService: OrderService
): OrderApi {

    @PostMapping("/api/v1/orders")
    override fun createOrder(
        @RequestBody request: OrderCreateRequest,
        @AuthenticationPrincipal loginUser: LoginUser
    ): ApiResponse<DefaultIdResponse> {
        val orderId = orderService.order(request.toOrder(loginUser.id), request.toOrderItem())

        return ApiResponse.success(DefaultIdResponse(orderId))
    }
}