package com.wanted.resourceserver.infra.db

import com.wanted.resourceserver.domain.product.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, Long> {
}