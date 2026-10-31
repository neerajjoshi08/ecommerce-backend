package com.example.product.persistence.entity

import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
open class BaseEntity(
    @CreatedDate open var createdDateTime: LocalDateTime? = null,
    @LastModifiedDate open var modifiedDateTime: LocalDateTime? = null,
)
