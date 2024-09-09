package com.wanted.resourceserver.domain.order

import com.wanted.resourceserver.domain.order.item.OrderItem

class Invoice(
    val order: Order,
    val orderItem: OrderItem
) {
}