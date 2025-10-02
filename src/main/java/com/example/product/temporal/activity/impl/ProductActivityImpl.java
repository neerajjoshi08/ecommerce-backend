package com.example.product.temporal.activity.impl;

import com.example.product.temporal.activity.ProductActivity;
import io.temporal.spring.boot.ActivityImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.example.product.temporal.Constants.PRODUCTS_TASK_QUEUE;

@Slf4j
@Component
@ActivityImpl(taskQueues = PRODUCTS_TASK_QUEUE)
public class ProductActivityImpl implements ProductActivity {

    @Override
    public String initializeProducts() {
        log.info("Inside initialize Products");
        return "Init products";
    }
}
