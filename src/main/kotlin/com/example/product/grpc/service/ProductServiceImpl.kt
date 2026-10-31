package com.example.product.grpc.service

import com.example.product.HelloRequest
import com.example.product.HelloResponse
import com.example.product.ListProductResponse
import com.example.product.ProductServiceGrpc
import com.example.product.grpc.validations.RequestValidator
import com.example.product.mapper.ProductProtoMapper
import com.example.product.service.ListProductService
import com.google.protobuf.Empty
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class ProductServiceImpl(
    private val listProductService: ListProductService,
) : ProductServiceGrpc.ProductServiceImplBase() {
    override fun listProducts(
        request: Empty,
        responseObserver: StreamObserver<ListProductResponse>,
    ) {
        RequestValidator.validateListProducts(request)

        val products = listProductService.listProducts()

        val productProtos =
            ProductProtoMapper.INSTANCE.productsDtoToProductsProto(products)

        val listProductResponse =
            ListProductResponse
                .newBuilder()
                .addAllProducts(productProtos)
                .build()

        responseObserver.onNext(listProductResponse)
        responseObserver.onError(StatusRuntimeException(Status.ALREADY_EXISTS))
        responseObserver.onCompleted()
    }

    override fun sayHello(
        responseObserver: StreamObserver<HelloResponse>,
    ): StreamObserver<HelloRequest> {
        val requestObserver = HelloRequestObserver()
        responseObserver.onNext(HelloResponse.newBuilder().setMessage("Hello! From Server").build())
        return requestObserver
    }

    class HelloRequestObserver : StreamObserver<HelloRequest> {
        override fun onNext(value: HelloRequest) {
            println("Received request: $value")
        }

        override fun onError(t: Throwable) {
        }

        override fun onCompleted() {
            println("Request is completed.")
        }
    }
}
