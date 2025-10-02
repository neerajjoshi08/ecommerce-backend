package com.example.product.grpc.exception;

import io.grpc.Metadata;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.recovery.GRpcExceptionHandler;
import org.lognet.springboot.grpc.recovery.GRpcExceptionScope;
import org.lognet.springboot.grpc.recovery.GRpcServiceAdvice;

@GRpcServiceAdvice
@Slf4j
public class ExceptionHandler {

    @GRpcExceptionHandler
    public Status MyCustomExceptionHandler(ProductException productException,
                                           GRpcExceptionScope scope) {

        log.error("Exception: {}", productException.toString());

        Metadata responseHeaders = scope.getResponseHeaders();
//    ErrorDetailsProto errorDetailsProto = ErrorDetailsProto.

        return Status.INTERNAL;
    }
}
