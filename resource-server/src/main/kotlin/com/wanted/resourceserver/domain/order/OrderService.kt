package com.wanted.resourceserver.domain.order

import com.wanted.resourceserver.domain.order.item.OrderItem
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderAppender: OrderAppender,
    private val orderValidator: OrderValidator
) {

    fun order(order: Order, orderItem: OrderItem): Long {
        orderValidator.validateOrderItem(orderItem)

        return orderAppender.append(order, orderItem).id
    }
}