package com.wanted.resourceserver.domain.order

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderUpdater(
    private val orderReader: OrderReader
) {
    @Transactional
    fun pay(orderId: Long) {
        val order = orderReader.read(orderId)
        order.pay()
    }

    @Transactional
    fun shipping(orderId: Long) {
        val order = orderReader.read(orderId)
        order.shipping()
    }
}