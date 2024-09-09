package com.wanted.resourceserver.domain.order

import java.time.LocalDate

data class PaginationRequest(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val limit: Int,
    val offset: Int,
    val orderType: OrderType
)
