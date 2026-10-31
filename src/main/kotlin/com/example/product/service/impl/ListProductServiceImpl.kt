package com.example.product.service.impl

import com.example.product.mapper.ProductEntityMapper
import com.example.product.persistence.repository.ProductRepository
import com.example.product.service.ListProductService
import com.example.product.service.dto.ProductDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ListProductServiceImpl(
    private val productRepository: ProductRepository,
) : ListProductService {
    private val log = LoggerFactory.getLogger(ListProductServiceImpl::class.java)

    override fun listProducts(): List<ProductDto> {
        val productEntities = productRepository.findAll()

        if (productEntities.isEmpty()) {
            log.warn("No products exists")
            return emptyList()
        }

        return ProductEntityMapper.INSTANCE.productsDtoToProductEntities(productEntities)
    }
}
