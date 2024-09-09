package com.wanted.resourceserver.domain.order

import com.wanted.resourceserver.infra.db.OrderItemRepository
import com.wanted.resourceserver.infra.db.OrderRepository
import com.wanted.resourceserver.support.LinksResponse
import com.wanted.resourceserver.support.PaginationResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class OrderReader(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository
) {

    fun getInvoices(request: PaginationRequest, userId: Long): PaginationResponse<Invoice> {
        val startOfDay = request.startDate.atStartOfDay()
        val endOfDay = request.endDate.plusDays(1).atStartOfDay().minusNanos(1)
        val orders =
            orderRepository.findByOrderDateBetweenAndTypeAndUserId(
                startOfDay,
                endOfDay,
                request.orderType,
                userId,
                PageRequest.of(request.offset / request.limit, request.limit, Sort.by(Sort.Direction.DESC, "orderDate"))
            )
        val orderIds = orders.content.map { it.id }
        val orderItemMap = orderItemRepository.findByOrderIdIn(orderIds).associateBy { it.orderId }

        val invoices = orders.content.map { Invoice(it, orderItemMap[it.id]!!) }
        val links = createLinks(request, orders.number, orders.totalPages)

        return PaginationResponse(
            page = orders.number + 1,
            totalPages = orders.totalPages,
            links = links,
            data = invoices
        )
    }

    private fun createLinks(request: PaginationRequest, currentPage: Int, totalPages: Int): LinksResponse {
        val baseUrl = "/api/v1/orders"
        val selfLink = "$baseUrl?startDate=${request.startDate}&endDate=${request.endDate}&orderType=${request.orderType}&offset=${request.offset}&limit=${request.limit}"
        val firstLink = "$baseUrl?startDate=${request.startDate}&endDate=${request.endDate}&orderType=${request.orderType}&offset=0&limit=${request.limit}"
        val lastLink = "$baseUrl?startDate=${request.startDate}&endDate=${request.endDate}&orderType=${request.orderType}&offset=${(totalPages - 1) * request.limit}&limit=${request.limit}"

        val previousLink = if (currentPage > 0) {
            "$baseUrl?startDate=${request.startDate}&endDate=${request.endDate}&orderType=${request.orderType}&offset=${(currentPage - 1) * request.limit}&limit=${request.limit}"
        } else null

        val nextLink = if (currentPage < totalPages - 1) {
            "$baseUrl?startDate=${request.startDate}&endDate=${request.endDate}&orderType=${request.orderType}&offset=${(currentPage + 1) * request.limit}&limit=${request.limit}"
        } else null

        return LinksResponse(selfLink, firstLink, previousLink, nextLink, lastLink)
    }
}