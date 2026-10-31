package com.example.product.service

import com.example.product.service.dto.ProductDto

interface ListProductService {

    fun listProducts(): List<ProductDto>
}
