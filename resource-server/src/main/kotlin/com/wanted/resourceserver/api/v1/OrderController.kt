package com.wanted.resourceserver.api.v1

import com.wanted.common.ApiResponse
import com.wanted.resourceserver.api.v1.request.OrderCreateRequest
import com.wanted.resourceserver.api.v1.response.DefaultIdResponse
import com.wanted.resourceserver.api.v1.response.InvoiceResponse
import com.wanted.resourceserver.domain.order.OrderService
import com.wanted.resourceserver.domain.order.OrderType
import com.wanted.resourceserver.domain.order.PaginationRequest
import com.wanted.resourceserver.security.LoginUser
import com.wanted.resourceserver.support.PaginationResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
class OrderController(
    private val orderService: OrderService,
): OrderApi {

    @PostMapping("/api/v1/orders")
    override fun createOrder(
        @RequestBody request: OrderCreateRequest,
        @AuthenticationPrincipal loginUser: LoginUser
    ): ApiResponse<DefaultIdResponse> {
        val orderId = orderService.order(request.toOrder(loginUser.id), request.toOrderItem())

        return ApiResponse.success(DefaultIdResponse(orderId))
    }

    @GetMapping("/api/v1/orders")
    override fun getOrders(
        @RequestParam startDate: LocalDate,
        @RequestParam endDate: LocalDate,
        @RequestParam(defaultValue = "20") limit: Int,
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam orderType: OrderType,
        @AuthenticationPrincipal loginUser: LoginUser
    ): ApiResponse<PaginationResponse<InvoiceResponse>> {
        val request = PaginationRequest(startDate, endDate, limit, offset, orderType)
        val result = orderService.getInvoices(request, loginUser.id)
            .map{ InvoiceResponse(it) }

        return ApiResponse.success(result)
    }

    @PostMapping("/api/v1/orders/{orderId}/payment")
    override fun pay(@PathVariable orderId: Long): ApiResponse<Void> {
        orderService.pay(orderId)

        return ApiResponse.success()
    }

    @PostMapping("/api/v1/orders/{orderId}/shipping")
    override fun shipping(@PathVariable orderId: Long): ApiResponse<Void> {
        orderService.shipping(orderId)

        return ApiResponse.success()
    }
}