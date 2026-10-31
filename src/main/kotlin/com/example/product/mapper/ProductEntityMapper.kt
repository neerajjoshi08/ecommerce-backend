package com.example.product.mapper

import com.example.product.persistence.entity.ProductEntity
import com.example.product.service.dto.ProductDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface ProductEntityMapper {
    companion object {
        val INSTANCE: ProductEntityMapper = Mappers.getMapper(ProductEntityMapper::class.java)
    }

    fun productDtoToProductEntity(productEntity: ProductEntity): ProductDto

    fun productsDtoToProductEntities(productEntities: List<ProductEntity>): List<ProductDto>
}
