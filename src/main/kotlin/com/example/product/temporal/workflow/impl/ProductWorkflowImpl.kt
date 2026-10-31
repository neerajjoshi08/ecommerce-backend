package com.example.product.temporal.workflow.impl

import com.example.product.temporal.Constants
import com.example.product.temporal.activity.ProductActivity
import com.example.product.temporal.workflow.ProductWorkflow
import io.temporal.activity.ActivityOptions
import io.temporal.common.RetryOptions
import io.temporal.spring.boot.WorkflowImpl
import io.temporal.workflow.Workflow
import org.slf4j.LoggerFactory
import java.time.Duration

@WorkflowImpl(taskQueues = [Constants.PRODUCTS_TASK_QUEUE])
class ProductWorkflowImpl : ProductWorkflow {

    private val log = LoggerFactory.getLogger(ProductWorkflowImpl::class.java)

    private val productActivity =
        Workflow.newActivityStub(ProductActivity::class.java, getActivityOptions())

    private fun getActivityOptions(): ActivityOptions {
        return ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(10))
            .setRetryOptions(
                RetryOptions.newBuilder().build()
            ).build()
    }

    override fun loadProducts(): String {
        log.info("Inside load")
        return productActivity.initializeProducts()
    }
}
