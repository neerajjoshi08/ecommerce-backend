package com.example.product.persistence.entity;

import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

  @CreatedDate
  private LocalDateTime createdDateTime;

  @LastModifiedDate
  private LocalDateTime modifiedDateTime;
}
