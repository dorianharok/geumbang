package com.wanted.resourceserver.domain.order

import com.wanted.resourceserver.fixture.OrderFixture
import com.wanted.resourceserver.infra.db.OrderItemRepository
import com.wanted.resourceserver.infra.db.OrderRepository
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class OrderAppenderTest {

    private val orderRepository = mockk<OrderRepository>()
    private val orderItemRepository = mockk<OrderItemRepository>()
    private val orderAppender = OrderAppender(orderRepository, orderItemRepository)

    @Test
    fun `주문을 생성한다`() {
        // given
        every { orderRepository.save(any()) } returns OrderFixture.order()
        every { orderItemRepository.save(any()) } returns OrderFixture.orderItem()
        val initOrder = OrderFixture.initOrder()
        val initOrderItem = OrderFixture.initOrderItem()

        // when
        val append = orderAppender.append(initOrder, initOrderItem)

        // then
        assertThat(append.id).isNotNull()
        assertThat(initOrderItem.orderId).isEqualTo(append.id)
    }
}