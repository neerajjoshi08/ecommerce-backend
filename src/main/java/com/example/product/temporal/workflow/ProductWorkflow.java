package com.example.product.temporal.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ProductWorkflow {

    @WorkflowMethod
    String loadProducts();
}
