package com.wanted.resourceserver.domain.order

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object OrderNumberGenerator {

    fun generateOrderNumber(orderType: OrderType): String {
        val now = LocalDateTime.now()
        val datePart = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val typePart = if (orderType == OrderType.PURCHASE) "P" else "S"
        val randomPart = generateRandomPart()

        return "$datePart-$typePart-$randomPart"
    }

    private fun generateRandomPart(): String {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8)
    }
}