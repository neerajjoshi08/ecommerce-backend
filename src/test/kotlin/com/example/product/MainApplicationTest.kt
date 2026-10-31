package com.example.product

import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import org.junit.jupiter.api.Test
import org.lognet.springboot.grpc.context.LocalRunningGrpcPort

import java.util.concurrent.Executors

class MainApplicationTest {

    @LocalRunningGrpcPort
    private val port: Int = 8080

    private val channel = ManagedChannelBuilder.forAddress("localhost", 6565)
            .usePlaintext()
            .build()

    private val productServiceStub = ProductServiceGrpc.newStub(channel)

    @Test
    fun `test Platform Threads`() {
        val executorService = Executors.newFixedThreadPool(10)
        val runnable = {
            println(
                "Working some work by thread: " + Thread.currentThread().name
            )
        }

        repeat(10) {
            executorService.submit(runnable)
        }
    }

    @Test
    fun `gRPC test`(){

        val responseObserver = HelloResponseObserver()
        val helloRequestStreamObserver = productServiceStub.sayHello(
                responseObserver)
        helloRequestStreamObserver.onNext(HelloRequest.newBuilder()
                .setName("First name").build())
    }
}

class HelloResponseObserver : StreamObserver<HelloResponse> {

    override fun onNext(value: HelloResponse ) {
        println("Received the response: $value")
    }

    override fun onError(t: Throwable) {

    }

    override fun onCompleted() {
        println("Response is completed.")
    }
}