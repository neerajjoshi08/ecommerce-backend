package com.example.product.grpc.exception;

import io.grpc.Status;
import io.grpc.Status.Code;
import io.grpc.StatusRuntimeException;
import lombok.Getter;

@Getter
public class ProductException extends StatusRuntimeException {

    private final Code code;
    private final String message;

    public ProductException(Status status, String message) {
        super(status);
        this.code = status.getCode();
        this.message = message;
    }

    @Override
    public String toString() {
        return "ProductException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
