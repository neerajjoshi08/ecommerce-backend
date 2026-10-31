package com.example.product.grpc.exception

import io.grpc.Status
import io.grpc.Status.Code
import io.grpc.StatusRuntimeException

class ProductException(status: Status, override val message: String) :
    StatusRuntimeException(status) {
    val code: Code = status.code
}
