package com.example.product.temporal.activity.impl

import com.example.product.temporal.Constants
import com.example.product.temporal.activity.ProductActivity
import io.temporal.spring.boot.ActivityImpl
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@ActivityImpl(taskQueues = [Constants.PRODUCTS_TASK_QUEUE])
class ProductActivityImpl : ProductActivity {
    private val log = LoggerFactory.getLogger(ProductActivityImpl::class.java)

    override fun initializeProducts(): String {
        log.info("Inside initialize Products")
        return "Init products"
    }
}
