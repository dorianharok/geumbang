package com.wanted.resourceserver.domain.product

import com.wanted.resourceserver.infra.db.ProductRepository
import com.wanted.resourceserver.support.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ProductReader(
    private val productRepository: ProductRepository
){

    fun read(productId: Long): Product {
        return productRepository.findByIdOrNull(productId) ?: throw EntityNotFoundException()
    }
}