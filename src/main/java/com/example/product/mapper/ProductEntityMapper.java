package com.example.product.mapper;

import com.example.product.persistence.entity.ProductEntity;
import com.example.product.service.dto.ProductDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductEntityMapper {

  ProductEntityMapper INSTANCE = Mappers.getMapper(ProductEntityMapper.class);


  ProductDto productDtoToProductEntity(ProductEntity productEntity);

  List<ProductDto> productsDtoToProductEntities(List<ProductEntity> productEntities);
}
