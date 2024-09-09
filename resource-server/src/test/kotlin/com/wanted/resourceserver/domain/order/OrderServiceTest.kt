package com.wanted.resourceserver.domain.order

import com.wanted.resourceserver.domain.ContainerTest
import com.wanted.resourceserver.exception.EntityNotFoundException
import com.wanted.resourceserver.exception.OrderStatusException
import com.wanted.resourceserver.fixture.OrderFixture
import com.wanted.resourceserver.fixture.ProductFixture
import com.wanted.resourceserver.exception.PriceMismatchException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal

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
    fun `주문을 생성 직후 상태는 주문 완료이다`() {
        // given
        val product = productRepository.save(ProductFixture.gold999())
        val initOrder = OrderFixture.initOrder()
        val initOrderItem = OrderFixture.createOrderItem(product.price, product.id, null)

        // when
        val orderId = orderService.order(initOrder, initOrderItem)

        // then
        val order = orderRepository.findByIdOrNull(orderId)
        assertThat(order!!.status).isEqualTo(OrderStatus.ORDER_COMPLETED)
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
    fun `결제가 완료되면 OrderStatus가 업데이트된다`() {
        // given
        val order = orderRepository.save(OrderFixture.initOrder())

        // when
        orderService.pay(order.id)

        // & then
        val findOrder = orderRepository.findByIdOrNull(order.id)
        assertThat(findOrder!!.status).isEqualTo(OrderStatus.PAYMENT_COMPLETED)
    }

    @Test
    fun `결제가 되지 않으면 배송을 할 수 없다`() {
        // given
        val order = orderRepository.save(OrderFixture.initOrder())

        // when & then
        assertThrows<OrderStatusException> {
            orderService.shipping(order.id)
        }
    }

    @Test
    fun `배송이 완료되면 OrderStatus가 업데이트된다`() {
        // given
        val order = orderRepository.save(OrderFixture.initOrder())
        orderService.pay(order.id)

        // when
        orderService.shipping(order.id)

        // & then
        val findOrder = orderRepository.findByIdOrNull(order.id)
        assertThat(findOrder!!.status).isEqualTo(OrderStatus.DELIVERY_COMPLETED)
    }

}