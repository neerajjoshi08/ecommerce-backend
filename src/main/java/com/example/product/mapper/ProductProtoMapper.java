package com.example.product.mapper;

import com.example.product.ProductProto;
import com.example.product.service.dto.ProductDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductProtoMapper {

  ProductProtoMapper INSTANCE = Mappers.getMapper(ProductProtoMapper.class);

  ProductProto productDtoToProductProto(ProductDto productDto);

  List<ProductProto> productsDtoToProductsProto(List<ProductDto> productsDto);
}
