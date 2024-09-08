package com.wanted.resourceserver.fixture

import com.wanted.resourceserver.domain.product.Product
import com.wanted.resourceserver.domain.product.GoldType
import java.math.BigDecimal

object ProductFixture {

    fun gold999() = Product(
        type = GoldType.GOLD_999,
        price = BigDecimal.valueOf(110000),
        id = 1L
    )

    fun gold9999() = Product(
        type = GoldType.GOLD_9999,
        price = BigDecimal.valueOf(100000),
        id = 2L
    )
}