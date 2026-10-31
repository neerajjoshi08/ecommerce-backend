package com.example.product.temporal.activity

import io.temporal.activity.ActivityInterface
import io.temporal.activity.ActivityMethod

@ActivityInterface
interface ProductActivity {

    @ActivityMethod
    fun initializeProducts(): String
}
