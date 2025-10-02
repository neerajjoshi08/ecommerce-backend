package com.example.product.temporal.workflow.impl;

import com.example.product.temporal.activity.ProductActivity;
import com.example.product.temporal.workflow.ProductWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static com.example.product.temporal.Constants.PRODUCTS_TASK_QUEUE;

@Slf4j
@WorkflowImpl(taskQueues = PRODUCTS_TASK_QUEUE)
public class ProductWorkflowImpl implements ProductWorkflow {

    private final ProductActivity productActivity = Workflow.newActivityStub(ProductActivity.class,
            getActivityOptions());

    private ActivityOptions getActivityOptions() {
        return ActivityOptions.newBuilder()
                .setStartToCloseTimeout(Duration.ofSeconds(10))
                .setRetryOptions(RetryOptions.newBuilder()
                        .build())
                .build();
    }

    @Override
    public String loadProducts() {
        log.info("Inside load");
        return productActivity.initializeProducts();
    }
}
