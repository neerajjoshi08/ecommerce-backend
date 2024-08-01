package com.example.product.grpc.service;

import com.example.product.ListProductResponse;
import com.example.product.ProductProto;
import com.example.product.ProductServiceGrpc;
import com.example.product.grpc.validations.RequestValidator;
import com.example.product.mapper.ProductProtoMapper;
import com.example.product.service.ListProductService;
import com.example.product.service.dto.ProductDto;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;

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
    responseObserver.onCompleted();
  }
}
