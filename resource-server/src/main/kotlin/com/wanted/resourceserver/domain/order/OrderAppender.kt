package com.wanted.resourceserver.domain.order

import com.wanted.resourceserver.domain.order.item.OrderItem
import com.wanted.resourceserver.infra.db.OrderItemRepository
import com.wanted.resourceserver.infra.db.OrderRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderAppender(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository
) {
    @Transactional
    fun append(order: Order, orderItem: OrderItem): Order {
        val appendedOrder = appendOrder(order)
        appendOrderItem(orderItem, appendedOrder.id)

        return appendedOrder
    }

    private fun appendOrder(order: Order): Order {
        return orderRepository.save(order)
    }

    private fun appendOrderItem(orderItem: OrderItem, orderId: Long): OrderItem {
        orderItem.orderId = orderId
        return orderItemRepository.save(orderItem)
    }
}