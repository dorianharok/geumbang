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
            throw PriceMismatchException("현재 금 시세 $price, 요청 시세 ${item.price}")
        }
    }

    private fun validateOrderItemGoldType(item: OrderItem, goldType: GoldType) {
        if(item.goldType != goldType) {
            throw GoldTypeMismatchException("요청 type ${item.goldType}, product gold type: $goldType")
        }
    }
}