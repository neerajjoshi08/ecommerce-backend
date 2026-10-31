package com.example.product.persistence.repository

import com.example.product.persistence.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductEntity, String>
