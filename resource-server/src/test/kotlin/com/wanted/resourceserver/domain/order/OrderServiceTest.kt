package com.wanted.resourceserver.domain.order

import com.wanted.resourceserver.domain.ContainerTest
import com.wanted.resourceserver.fixture.OrderFixture
import com.wanted.resourceserver.fixture.ProductFixture
import com.wanted.resourceserver.support.PriceMismatchException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal
import java.time.LocalDateTime

class OrderServiceTest: ContainerTest() {

    @AfterEach
    fun tearDown() {
        orderRepository.deleteAllInBatch()
        orderItemRepository.deleteAllInBatch()
        productRepository.deleteAllInBatch()
    }

    @Test
    fun `주문을 생성한다`() {
        // given
        val product = productRepository.save(ProductFixture.gold999())
        val initOrder = OrderFixture.initOrder()
        val initOrderItem = OrderFixture.createOrderItem(product.price, product.id, null)

        // when
        val orderId = orderService.order(initOrder, initOrderItem)

        // then
        assertThat(orderRepository.findByIdOrNull(orderId)).isNotNull
        assertThat(orderItemRepository.findAll().size).isOne
    }

    @Test
    fun `주문 아이템이 저장될 때 orderId가 함께 저장된다`() {
        // given
        val product = productRepository.save(ProductFixture.gold999())
        val initOrder = OrderFixture.initOrder()
        val initOrderItem = OrderFixture.createOrderItem(product.price, product.id, null)

        // when
        val orderId = orderService.order(initOrder, initOrderItem)

        // then
        assertThat(orderItemRepository.findByOrderId(orderId)).isNotEmpty
    }

    @Test
    fun `현재 금값과 주문 가격이 다를 경우 주문할 수 없다`() {
        // given
        val product = productRepository.save(ProductFixture.gold999())
        val initOrder = OrderFixture.initOrder()
        val initOrderItem = OrderFixture.createOrderItem(BigDecimal.valueOf(88000), product.id, null)

        // when & then
        assertThrows<PriceMismatchException> {
            orderService.order(initOrder, initOrderItem)
        }
    }

}