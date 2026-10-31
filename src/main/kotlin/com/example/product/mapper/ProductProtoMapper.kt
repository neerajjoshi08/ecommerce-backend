package com.example.product.mapper

import com.example.product.ProductProto
import com.example.product.service.dto.ProductDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface ProductProtoMapper {
    companion object {
        val INSTANCE: ProductProtoMapper = Mappers.getMapper(ProductProtoMapper::class.java)
    }

    fun productDtoToProductProto(productDto: ProductDto): ProductProto

    fun productsDtoToProductsProto(productsDto: List<ProductDto>): List<ProductProto>
}
