package com.example.product.temporal.activity;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ProductActivity {

    @ActivityMethod
    String initializeProducts();
}
