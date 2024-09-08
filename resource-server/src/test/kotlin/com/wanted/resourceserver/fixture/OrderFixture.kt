package com.wanted.resourceserver.fixture

import com.wanted.resourceserver.domain.order.Order
import com.wanted.resourceserver.domain.order.OrderStatus
import com.wanted.resourceserver.domain.order.OrderType
import com.wanted.resourceserver.domain.order.item.OrderItem
import com.wanted.resourceserver.domain.product.GoldType
import java.math.BigDecimal
import java.time.LocalDateTime

object OrderFixture {

    fun initOrder() = Order(
        orderNumber = "20240909-P-abcd1234",
        orderDate = LocalDateTime.of(2024, 9, 9, 0, 2, 34),
        userId = 1L,
        status = OrderStatus.ORDER_COMPLETED,
        type = OrderType.PURCHASE,
        shippingAddress = "서울시",
        totalPrice = BigDecimal.valueOf(30000),
    )

    fun order() = Order(
        orderNumber = "20240909-P-abcd1234",
        orderDate = LocalDateTime.of(2024, 9, 9, 0, 2, 34),
        userId = 1L,
        status = OrderStatus.ORDER_COMPLETED,
        type = OrderType.PURCHASE,
        shippingAddress = "서울시",
        totalPrice = BigDecimal.valueOf(30000),
        id = 1L
    )

    fun initOrderItem() = OrderItem(
        goldType = GoldType.GOLD_999,
        quantity = BigDecimal.valueOf(3.00),
        price = BigDecimal.valueOf(10000),
        productId = 1L,
        orderId = 0,
    )

    fun orderItem() = OrderItem(
        goldType = GoldType.GOLD_999,
        quantity = BigDecimal.valueOf(3.00),
        price = BigDecimal.valueOf(10000),
        productId = 1L,
        orderId = 1L,
        id = 1L
    )

    fun createOrderItem(price: BigDecimal, productId: Long, quantity: BigDecimal?) = OrderItem(
        goldType = GoldType.GOLD_999,
        quantity = quantity ?: BigDecimal.valueOf(3.00),
        price = price,
        productId = productId,
        orderId = 0
    )
}