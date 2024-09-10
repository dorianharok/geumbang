package com.wanted.resourceserver.domain.product

import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productReader: ProductReader
) {

    fun readAll(): List<Product> {
        return productReader.readAll()
    }
}