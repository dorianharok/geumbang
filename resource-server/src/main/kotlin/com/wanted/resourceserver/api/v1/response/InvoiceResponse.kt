package com.wanted.resourceserver.api.v1.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.wanted.resourceserver.domain.order.Invoice
import com.wanted.resourceserver.domain.order.OrderType
import com.wanted.resourceserver.domain.product.GoldType
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.time.LocalDateTime

data class InvoiceResponse(
    @Schema(description = "주문 id", example = "1")
    val id: Long,
    @Schema(description = "주문 번호", example = "20240909-P-3a2f925b")
    val orderNumber: String,
    @Schema(description = "주문 날짜", example = "2024-09-09 03:36:52")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val orderDate: LocalDateTime,
    @Schema(description = "주문 상태", example = "주문 완료")
    val status: String,
    @Schema(description = "주문 유형", example = "PURCHASE")
    val type: OrderType,
    @Schema(description = "배송 주소", example = "서울시 송파구")
    val shippingAddress: String,
    @Schema(description = "총 주문 금액", example = "3990.00")
    val totalPrice: String,
    val orderItem: OrderItemResponse
) {
    constructor(invoice: Invoice) : this(
        id = invoice.order.id,
        orderNumber = invoice.order.orderNumber,
        orderDate = invoice.order.orderDate,
        status = invoice.order.status.getDescription(invoice.order.type),
        type = invoice.order.type,
        shippingAddress = invoice.order.shippingAddress,
        totalPrice = invoice.order.totalPrice.toPlainString(),
        orderItem = OrderItemResponse(
            goldType = invoice.orderItem.goldType,
            quantity = invoice.orderItem.quantity.toPlainString(),
            price = invoice.orderItem.price.toPlainString(),
            productId = invoice.orderItem.productId,
        )
    )
}

data class OrderItemResponse(
    @Schema(description = "금 타입")
    val goldType: GoldType,
    @Schema(description = "주문 수량", example = "3.99")
    val quantity: String,
    @Schema(description = "주문 당시 금 가격", example = "1000.00")
    val price: String,
    val productId: Long,
)
