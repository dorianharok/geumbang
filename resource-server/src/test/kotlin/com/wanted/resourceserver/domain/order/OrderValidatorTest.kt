package com.wanted.resourceserver.domain.order

import com.wanted.resourceserver.domain.order.item.OrderItem
import com.wanted.resourceserver.domain.product.GoldType
import com.wanted.resourceserver.domain.product.ProductReader
import com.wanted.resourceserver.fixture.ProductFixture
import com.wanted.resourceserver.exception.GoldTypeMismatchException
import com.wanted.resourceserver.exception.PriceMismatchException
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal

@ExtendWith(MockKExtension::class)
class OrderValidatorTest {

    private val productReader = mockk<ProductReader>()
    private val orderValidator = OrderValidator(productReader)

    @Test
    fun `오더 아이템의 가격과 현재 금 가격이 다를 경우 주문할 수 없다`() {
        // given
        every { productReader.read(any()) } returns ProductFixture.gold999()
        val orderItem = OrderItem(
            goldType = GoldType.GOLD_999,
            quantity = BigDecimal.valueOf(3.99),
            price = BigDecimal.valueOf(103000),
            productId = 0,
            orderId = 0,
        )

        // when & then
        assertThrows<PriceMismatchException> {
            orderValidator.validateOrderItem(orderItem)
        }
    }

    @Test
    fun `오더 아이템의 품목이 실제 상품 품목과 다를 경우 주문할 수 없다`() {
        // given
        every { productReader.read(any()) } returns ProductFixture.gold999()
        val orderItem = OrderItem(
            goldType = GoldType.GOLD_9999,
            quantity = BigDecimal.valueOf(3.99),
            price = BigDecimal.valueOf(110000),
            productId = 0,
            orderId = 0,
        )

        // when & then
        assertThrows<GoldTypeMismatchException> {
            orderValidator.validateOrderItem(orderItem)
        }
    }

    @Test
    fun `오더 아이템의 가격과 품목이 일치할 경우 주문할 수 있다`() {
        // given
        every { productReader.read(any()) } returns ProductFixture.gold999()
        val orderItem = OrderItem(
            goldType = GoldType.GOLD_999,
            quantity = BigDecimal.valueOf(3.99),
            price = BigDecimal.valueOf(110000),
            productId = 0,
            orderId = 0,
        )

        // when & then
        assertDoesNotThrow {
            orderValidator.validateOrderItem(orderItem)
        }
    }
}