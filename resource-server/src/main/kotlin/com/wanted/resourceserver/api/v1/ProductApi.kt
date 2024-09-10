package com.wanted.resourceserver.api.v1

import com.wanted.common.ApiResponse
import com.wanted.resourceserver.api.v1.response.ProductResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "상품 API")
interface ProductApi {

    fun getProducts(): ApiResponse<List<ProductResponse>>
}