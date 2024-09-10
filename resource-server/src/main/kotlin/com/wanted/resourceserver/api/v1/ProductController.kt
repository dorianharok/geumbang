package com.wanted.resourceserver.api.v1

import com.wanted.common.ApiResponse
import com.wanted.resourceserver.api.v1.response.ProductResponse
import com.wanted.resourceserver.domain.product.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val productService: ProductService
): ProductApi {

    @GetMapping("/api/v1/products")
    override fun getProducts(): ApiResponse<List<ProductResponse>> {
        val products = productService.readAll().map { ProductResponse(it) }

        return ApiResponse.success(products)
    }
}