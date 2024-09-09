package com.wanted.resourceserver.api.v1

import com.wanted.common.ApiResponse
import com.wanted.resourceserver.api.v1.request.OrderCreateRequest
import com.wanted.resourceserver.api.v1.response.DefaultIdResponse
import com.wanted.resourceserver.api.v1.response.InvoiceResponse
import com.wanted.resourceserver.domain.order.OrderType
import com.wanted.resourceserver.security.LoginUser
import com.wanted.resourceserver.support.PaginationResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate

@Tag(name = "주문 API")
interface OrderApi {
    @Operation(summary = "주문 생성 API")
    fun createOrder(@RequestBody request: OrderCreateRequest,
                    @AuthenticationPrincipal loginUser: LoginUser): ApiResponse<DefaultIdResponse>

    @Operation(summary = "주문 조회 API")
    fun getOrders(
        @Parameter(description = "시작 날짜[yyyy-MM-dd]", example = "2024-09-01", required = true)
        @RequestParam startDate: LocalDate,

        @Parameter(description = "종료 날짜[yyyy-MM-dd]", example = "2024-09-30", required = true)
        @RequestParam endDate: LocalDate,

        @Parameter(description = "페이지 크기", example = "20")
        @RequestParam(defaultValue = "20") limit: Int,

        @Parameter(description = "페이지 오프셋", example = "0")
        @RequestParam(defaultValue = "0") offset: Int,

        @Parameter(description = "주문 타입", example = "PURCHASE", required = true)
        @RequestParam orderType: OrderType,

        @AuthenticationPrincipal loginUser: LoginUser
    ): ApiResponse<PaginationResponse<InvoiceResponse>>
}