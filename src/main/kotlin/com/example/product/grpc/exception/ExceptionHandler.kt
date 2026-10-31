package com.example.product.grpc.exception

import io.grpc.Status
import org.lognet.springboot.grpc.recovery.GRpcExceptionHandler
import org.lognet.springboot.grpc.recovery.GRpcExceptionScope
import org.lognet.springboot.grpc.recovery.GRpcServiceAdvice
import org.slf4j.LoggerFactory

@GRpcServiceAdvice
class ExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @GRpcExceptionHandler
    fun myCustomExceptionHandler(
        productException: ProductException,
        scope: GRpcExceptionScope
    ): Status {

        log.error("Exception: {}", productException.toString())

        val responseHeaders = scope.getResponseHeaders()
        return Status.INTERNAL
    }
}
