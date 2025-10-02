package com.example.product.mapper;

import com.example.product.ProductProto;
import com.example.product.service.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductProtoMapper {

    ProductProtoMapper INSTANCE = Mappers.getMapper(ProductProtoMapper.class);

    ProductProto productDtoToProductProto(ProductDto productDto);

    List<ProductProto> productsDtoToProductsProto(List<ProductDto> productsDto);
}
