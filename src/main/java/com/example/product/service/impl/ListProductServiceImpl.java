package com.example.product.service.impl;

import com.example.product.mapper.ProductEntityMapper;
import com.example.product.persistence.entity.ProductEntity;
import com.example.product.persistence.repository.ProductRepository;
import com.example.product.service.ListProductService;
import com.example.product.service.dto.ProductDto;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListProductServiceImpl implements ListProductService {

  private final ProductRepository productRepository;

  @Override
  public List<ProductDto> listProducts() {
    List<ProductEntity> productEntities = productRepository.findAll();

    if (productEntities.isEmpty()) {
      log.warn("No products exists");
      return Collections.emptyList();
    }

    return ProductEntityMapper.INSTANCE.productsDtoToProductEntities(productEntities);
  }
}
