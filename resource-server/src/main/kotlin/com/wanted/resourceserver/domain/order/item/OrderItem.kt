package com.wanted.resourceserver.domain.order.item

import com.wanted.resourceserver.domain.product.GoldType
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "order_items")
class OrderItem(
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val goldType: GoldType,

    @Column(nullable = false, precision = 10, scale = 2)
    val quantity: BigDecimal,

    @Column(nullable = false, precision = 10, scale = 2)
    val price: BigDecimal,

    @Column(nullable = false, precision = 10, scale = 2)
    val productId: Long,

    @Column(nullable = false, precision = 10, scale = 2)
    var orderId: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) {
}