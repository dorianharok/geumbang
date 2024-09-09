package com.wanted.resourceserver.infra.db

import com.wanted.resourceserver.domain.order.Order
import com.wanted.resourceserver.domain.order.OrderType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface OrderRepository: JpaRepository<Order, Long> {
    fun findByOrderDateBetweenAndTypeAndUserId(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        type: OrderType,
        userId: Long,
        pageable: Pageable
    ): Page<Order>
}