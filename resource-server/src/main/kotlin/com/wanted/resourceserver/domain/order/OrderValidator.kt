package com.wanted.resourceserver.domain.order

import com.wanted.resourceserver.domain.order.item.OrderItem
import com.wanted.resourceserver.domain.product.GoldType
import com.wanted.resourceserver.domain.product.ProductReader
import com.wanted.resourceserver.support.GoldTypeMismatchException
import com.wanted.resourceserver.support.PriceMismatchException
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class OrderValidator(
    private val productReader: ProductReader
) {
    fun validateOrderItem(item: OrderItem) {
        val product = productReader.read(item.productId)

        validateOrderItemPrice(item, product.price)
        validateOrderItemGoldType(item, product.type)
    }

    private fun validateOrderItemPrice(item: OrderItem, price: BigDecimal) {
        if(item.price != price) {
            throw PriceMismatchException()
        }
    }

    private fun validateOrderItemGoldType(item: OrderItem, goldType: GoldType) {
        if(item.goldType != goldType) {
            throw GoldTypeMismatchException()
        }
    }
}