package com.example.product.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "products")
public class ProductEntity extends BaseEntity {

  @Id
  private String id;

  @Column
  private String name;

  @Column
  private String description;

  @Column
  private double price;
}
