package com.wanted.resourceserver.api.v1.request

import com.wanted.resourceserver.domain.order.Order
import com.wanted.resourceserver.domain.order.OrderNumberGenerator
import com.wanted.resourceserver.domain.order.OrderStatus
import com.wanted.resourceserver.domain.order.OrderType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class OrderCreateRequest(
    @Schema(description = "주문 타입 [PURCHASE, SALE]", example = "PURCHASE")
    @field:NotBlank(message = "주문 유형은 필수입니다.")
    val type: OrderType,
    @field:NotBlank(message = "상품은 필수입니다.")
    val orderItem: OrderItemCreateRequest,
    @Schema(description = "배송지", example = "서울시 송파구")
    @field:NotBlank(message = "배송지는 필수입니다.")
    val shippingAddress: String
) {
    fun toOrder(userId: Long) = Order(
        orderNumber = OrderNumberGenerator.generateOrderNumber(type),
        orderDate = LocalDateTime.now(),
        userId = userId,
        status = OrderStatus.ORDER_COMPLETED,
        type = type,
        totalPrice = orderItem.calculateTotalPrice(),
        shippingAddress = shippingAddress,
    )

    fun toOrderItem() = orderItem.toOrderItem()
}
