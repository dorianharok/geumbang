package com.wanted.resourceserver.infra.db

import com.wanted.resourceserver.domain.order.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<Order, Long> {
}