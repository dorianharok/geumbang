package com.wanted.resourceserver.domain.order

import com.wanted.resourceserver.domain.order.item.OrderItem
import com.wanted.resourceserver.support.PaginationResponse
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderAppender: OrderAppender,
    private val orderValidator: OrderValidator,
    private val orderReader: OrderReader
) {
    fun order(order: Order, orderItem: OrderItem): Long {
        orderValidator.validateOrderItem(orderItem)

        return orderAppender.append(order, orderItem).id
    }

    fun getInvoices(request:PaginationRequest, userId: Long): PaginationResponse<Invoice> {
        return orderReader.getInvoices(request, userId)
    }
}