package com.wanted.resourceserver.infra.db

import com.wanted.resourceserver.domain.order.item.OrderItem
import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemRepository: JpaRepository<OrderItem, Long> {
    fun findByOrderId(orderId: Long): List<OrderItem>
}