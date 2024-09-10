package com.wanted.resourceserver.api.v1.response

import com.wanted.resourceserver.domain.product.GoldType
import com.wanted.resourceserver.domain.product.Product

data class ProductResponse(
    val type: GoldType,
    val price: String,
    val id: Long
) {
    constructor(product: Product) : this(
        type = product.type,
        price = product.price.toPlainString(),
        id = product.id
    )
}
