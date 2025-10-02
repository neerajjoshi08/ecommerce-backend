package com.example.product;

import com.example.product.ProductServiceGrpc.ProductServiceStub;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.lognet.springboot.grpc.context.LocalRunningGrpcPort;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@SpringBootApplication
class MainApplicationTest {

    @LocalRunningGrpcPort
    int port;

    Channel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
            .usePlaintext()
            .build();

    ProductServiceStub productServiceStub = ProductServiceGrpc.newStub(channel);

    @Test
    public void testPlatformThreads() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Runnable runnable = () -> System.out.println(
                "Working some work by thread: " + Thread.currentThread().getName());

        for (int i = 0; i < 10; i++) {
            executorService.submit(runnable);
        }
    }

    @Test
    public void gRPC_test() {

        StreamObserver<HelloResponse> responseObserver = new HelloResponseObserver();
        StreamObserver<HelloRequest> helloRequestStreamObserver = productServiceStub.sayHello(
                responseObserver);
        helloRequestStreamObserver.onNext(HelloRequest.newBuilder()
                .setName("First name").build());
    }
}

class HelloResponseObserver implements StreamObserver<HelloResponse> {

    @Override
    public void onNext(HelloResponse value) {
        System.out.println("Received the response: " + value);
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onCompleted() {
        System.out.println("Response is completed.");
    }
}