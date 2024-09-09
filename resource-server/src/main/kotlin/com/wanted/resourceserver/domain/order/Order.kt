package com.wanted.resourceserver.domain.order

import com.wanted.resourceserver.exception.OrderStatusException
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @Column(unique = true, nullable = false)
    val orderNumber: String,

    @Column(nullable = false)
    val orderDate: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false, updatable = false)
    val userId: Long,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: OrderStatus,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: OrderType,

    @Column(nullable = false)
    val shippingAddress: String,

    @Column(nullable = false)
    val totalPrice: BigDecimal,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) {
    fun pay() {
        if(!status.canPay(this.status)) {
            throw OrderStatusException("요청 status: ${OrderStatus.PAYMENT_COMPLETED}, 현재 status: ${this.status}")
        }
        this.status = OrderStatus.PAYMENT_COMPLETED
    }

    fun shipping() {
        if(!status.canShipping(this.status)) {
            throw OrderStatusException("요청 status: ${OrderStatus.DELIVERY_COMPLETED}, 현재 status: ${this.status}")
        }

        this.status = OrderStatus.DELIVERY_COMPLETED
    }
}

enum class OrderType {
    PURCHASE, SALE
}

enum class OrderStatus(val purchaseDescription: String, val saleDescription: String) {
    ORDER_COMPLETED("주문 완료", "주문 완료"),
    PAYMENT_COMPLETED("입금 완료", "송금 완료"),
    DELIVERY_COMPLETED("발송 완료", "수령 완료");

    fun getDescription(orderType: OrderType): String {
        return when (orderType) {
            OrderType.PURCHASE -> purchaseDescription
            OrderType.SALE -> saleDescription
        }
    }

    fun canPay(status: OrderStatus): Boolean = status == ORDER_COMPLETED

    fun canShipping(status: OrderStatus): Boolean = status == PAYMENT_COMPLETED
}