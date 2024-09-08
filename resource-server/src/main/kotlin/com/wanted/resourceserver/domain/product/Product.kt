package com.wanted.resourceserver.domain.product

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "products")
class Product(
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    val type: GoldType,

    @Column(nullable = false, precision = 10, scale = 0)
    var price: BigDecimal,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) {
}

enum class GoldType {
    GOLD_999,
    GOLD_9999
}