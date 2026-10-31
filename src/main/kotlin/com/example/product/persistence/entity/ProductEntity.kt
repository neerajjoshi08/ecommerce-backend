package com.example.product.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "products")
open class ProductEntity(
    @Id open val id: String = "",
    @Column open var name: String = "",
    @Column open var description: String = "",
    @Column open var price: Double = 0.0,
) : BaseEntity()
