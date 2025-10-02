package com.example.product

import com.example.ChatRequest
import com.example.ChatResponse
import com.example.HelloServiceGrpc
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GrpcIntegrationTest {
    private lateinit var requestObserver: StreamObserver<ChatRequest>
    private val countDownLatch = CountDownLatch(1)


    @BeforeAll
    fun beforeAll() {
        val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build()

        val stub = HelloServiceGrpc.newStub(channel)
        requestObserver =
            stub.chat(object : StreamObserver<ChatResponse> {
                override fun onNext(value: ChatResponse) {
                    LoggerUtil.info("Got a response from the server...")
                    LoggerUtil.info(value.toString())
                }

                override fun onError(t: Throwable?) {
                }

                override fun onCompleted() {
                    countDownLatch.countDown()
                    LoggerUtil.info("Stream completed.")
                }
            })
    }

    @Test
    fun testStreamingRequest() {
        Thread {
            for (i in 1..100) {
                val request = ChatRequest.newBuilder()
                    .setMessage("hello-$i")
                    .build()
                synchronized(requestObserver) {
                    requestObserver.onNext(request)
                }
//                TimeUnit.SECONDS.sleep(1)
            }
            LoggerUtil.info("I'm about to complete the request...")
        }.start()

        Thread {
            for (i in 1..100) {
                val request = ChatRequest.newBuilder()
                    .setMessage("world-$i")
                    .build()
                synchronized(requestObserver) {
                    requestObserver.onNext(request)
                }
//                TimeUnit.SECONDS.sleep(1)
            }
            LoggerUtil.info("I'm about to complete the request...")
            requestObserver.onCompleted()
        }.start()

        countDownLatch.await(60, TimeUnit.SECONDS)
    }
}