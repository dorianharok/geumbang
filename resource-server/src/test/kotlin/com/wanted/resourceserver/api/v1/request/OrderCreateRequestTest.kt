package com.wanted.resourceserver.api.v1.request

import com.wanted.resourceserver.domain.order.OrderType
import com.wanted.resourceserver.domain.product.GoldType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal

class OrderCreateRequestTest {

    @Test
    fun `주문 번호가 생성된다`() {
        // given
        val orderItemRequest = OrderItemCreateRequest(
            goldType = GoldType.GOLD_999,
            quantity = "3.58",
            price = "384570",
            productId = 1L
        )
        val orderRequest = OrderCreateRequest(
            type = OrderType.PURCHASE,
            orderItem = orderItemRequest,
            shippingAddress = "address"
        )

        // when
        val order = orderRequest.toOrder(1L)

        // then
        assertThat(order.orderNumber).isNotNull()
    }

    @Test
    fun `주문 금액이 계산된다`() {
        // given
        val orderItemRequest = OrderItemCreateRequest(
            goldType = GoldType.GOLD_999,
            quantity = "3.58",
            price = "384570",
            productId = 1L
        )
        val orderRequest = OrderCreateRequest(
            type = OrderType.PURCHASE,
            orderItem = orderItemRequest,
            shippingAddress = "address"
        )

        // when
        val order = orderRequest.toOrder(1L)

        // then
        val totalPrice = BigDecimal("3.58").multiply(BigDecimal("384570")).setScale(2)
        assertThat(order.totalPrice).isEqualTo(totalPrice)
    }
}