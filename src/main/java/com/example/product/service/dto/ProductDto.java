package com.example.product.service.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public class ProductDto {

    String id;
    String name;
    String description;
    double price;
}
