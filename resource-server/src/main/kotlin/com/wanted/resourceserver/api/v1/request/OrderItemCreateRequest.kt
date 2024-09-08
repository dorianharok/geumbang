package com.wanted.resourceserver.api.v1.request

import com.wanted.resourceserver.domain.order.item.OrderItem
import com.wanted.resourceserver.domain.product.GoldType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal
import java.math.RoundingMode

data class OrderItemCreateRequest(
    @Schema(description = "상품 유형 [GOLD_999, GOLD_9999]", example = "GOLD_999")
    @field:NotBlank(message = "상품 유형은 필수입니다.")
    val goldType: GoldType,
    @Schema(description = "주문 수량", example = "3.99")
    @field:NotBlank(message = "수량은 필수입니다.")
    val quantity: String,
    @Schema(description = "상품 현재 가격", example = "460000")
    @field:NotBlank(message = "가격은 필수입니다.")
    val price: String,
    @Schema(description = "상품 아이디", example = "1")
    @field:NotBlank(message = "상품 아이디는 필수입니다.")
    val productId: Long,
) {
    fun toOrderItem() = OrderItem(
        goldType = goldType,
        quantity = quantity.toBigDecimal().setScale(2, RoundingMode.FLOOR),
        price = price.toBigDecimal().setScale(2, RoundingMode.FLOOR),
        productId = productId,
        orderId = 0,
    )

    fun calculateTotalPrice(): BigDecimal {
        val quantity = quantity.toBigDecimal().setScale(2, RoundingMode.FLOOR)
        val price = price.toBigDecimal().setScale(2, RoundingMode.FLOOR)

        return quantity.multiply(price).setScale(2, RoundingMode.FLOOR)
    }
}
