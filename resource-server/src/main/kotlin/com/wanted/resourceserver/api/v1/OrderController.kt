package com.wanted.resourceserver.api.v1

import com.wanted.common.ApiResponse
import com.wanted.resourceserver.api.v1.request.OrderCreateRequest
import com.wanted.resourceserver.api.v1.response.DefaultIdResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController: OrderApi {
    override fun createOrder(request: OrderCreateRequest): ApiResponse<DefaultIdResponse> {
        return ApiResponse.success(DefaultIdResponse(1L))
    }
}