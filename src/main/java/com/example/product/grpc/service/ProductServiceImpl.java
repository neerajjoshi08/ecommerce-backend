package com.example.product.grpc.service;

import com.example.product.*;
import com.example.product.grpc.validations.RequestValidator;
import com.example.product.mapper.ProductProtoMapper;
import com.example.product.service.ListProductService;
import com.example.product.service.dto.ProductDto;
import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;

import java.util.List;

@GRpcService
@RequiredArgsConstructor
public class ProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {

    private final ListProductService listProductService;

    @Override
    public void listProducts(Empty request, StreamObserver<ListProductResponse> responseObserver) {
        RequestValidator.validateListProducts(request);

        List<ProductDto> products = listProductService.listProducts();

        List<ProductProto> productProtos = ProductProtoMapper.INSTANCE.productsDtoToProductsProto(
                products);

        ListProductResponse listProductResponse = ListProductResponse.newBuilder()
                .addAllProducts(productProtos)
                .build();

        responseObserver.onNext(listProductResponse);
        responseObserver.onError(new StatusRuntimeException(Status.ALREADY_EXISTS));
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<HelloRequest> sayHello(StreamObserver<HelloResponse> responseObserver) {
        StreamObserver<HelloRequest> requestObserver = new HelloRequestObserver();
        responseObserver.onNext(HelloResponse.newBuilder().setMessage("Hello! From Server").build());
        return requestObserver;
    }

    class HelloRequestObserver implements StreamObserver<HelloRequest> {

        @Override
        public void onNext(HelloRequest value) {
            System.out.println("Received request: " + value);
        }

        @Override
        public void onError(Throwable t) {
        }

        @Override
        public void onCompleted() {
            System.out.println("Request is completed.");
        }
    }
}
