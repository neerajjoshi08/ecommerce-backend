//package com.example.product;
//
//import com.example.HelloServiceGrpc;
//import com.example.HelloServiceGrpc.HelloServiceStub;
//import com.example.HelloServiceOuterClass.ChatRequest;
//import com.example.HelloServiceOuterClass.ChatResponse;
//import io.grpc.Channel;
//import io.grpc.ManagedChannelBuilder;
//import io.grpc.stub.StreamObserver;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//import org.junit.jupiter.api.RepeatedTest;
//import org.junit.jupiter.api.Test;
//
//public class StreamingResponseTest {
//
//  int count = 10;
//  final CountDownLatch latch = new CountDownLatch(count);
//
//
//
/// /  @Test /  void test_streaming_from_python_server() throws InterruptedException { /
/// ExecutorService executorService = Executors.newFixedThreadPool(3); / /    Channel channel =
/// ManagedChannelBuilder.forAddress("localhost", 50051) /        .usePlaintext() /        .build();
/// / /    HelloServiceStub stub = HelloServiceGrpc.newStub(channel); /
/// StreamObserver<ChatRequest> chatRequestStreamObserver = stub.chat(chatResponseStreamObserver); /
/// /    executorService.submit(runTask(chatRequestStreamObserver)); /
/// executorService.submit(runTask(chatRequestStreamObserver)); /
/// executorService.submit(runTask(chatRequestStreamObserver)); / /    latch.await(); /
/// chatRequestStreamObserver.onCompleted(); / /    executorService.shutdown(); /  }
//
//  private Runnable runTask(StreamObserver<ChatRequest> chatRequestStreamObserver) {
//    return () -> {
//      System.out.println("Inside service" + Thread.currentThread().getName());
//      for (int i = 0; i < 5; i++) {
//        chatRequestStreamObserver.onNext(ChatRequest.newBuilder()
//            .setMessage("Hello-" + Thread.currentThread().getName() + "--" + i)
//            .build());
//
//        try {
//          TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//          throw new RuntimeException(e);
//        }
//      }
//
//
//    };
//  }
//
//  @Test
//  void test_streaming_from_python_server1() throws InterruptedException {
//    // Executor for concurrent task execution
//    ExecutorService executorService = Executors.newFixedThreadPool(count);
//
//    // Setup the gRPC channel
//    Channel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
//        .usePlaintext()
//        .build();
//
//    // Stub to make async calls
//    HelloServiceStub stub = HelloServiceGrpc.newStub(channel);
//
//    // Create the StreamObserver for sending messages to the server
//
//    // Submit multiple tasks that send requests asynchronously
//    for (int i = 0; i < count; i++) {
//      StreamObserver<ChatResponse> chatResponseStreamObserver = new StreamObserver<>() {
//        @Override
//        public void onNext(ChatResponse value) {
//          System.out.println("Received ChatResponse output: " + value);
//        }
//
//        @Override
//        public void onError(Throwable t) {
//          System.err.println("Error: " + t);
//        }
//
//        @Override
//        public void onCompleted() {
//          System.out.println("Received ChatResponse completed");
//        }
//      };
//      StreamObserver<ChatRequest> chatRequestStreamObserver = stub.chat(chatResponseStreamObserver);
//      executorService.submit(runTask1(chatRequestStreamObserver));
//    }
//
//    // Wait for the tasks to finish (give it enough time for the server to respond)
//    latch.await();
//    executorService.shutdown();
//  }
//
//  // Helper method to create a task that sends messages to the server
//  private Runnable runTask1(StreamObserver<ChatRequest> chatRequestStreamObserver) {
//    return () -> {
//      System.out.println("Sending messages from thread: " + Thread.currentThread().getName());
//      for (int i = 0; i < 5; i++) {
//        // Send a message with a custom thread name to show which thread is sending it
//        chatRequestStreamObserver.onNext(ChatRequest.newBuilder()
//            .setMessage("Hello from " + Thread.currentThread().getName() + " - Message " + i)
//            .build());
//
//        try {
//          TimeUnit.SECONDS.sleep(1);  // Simulate some delay between messages
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
//      }
//
//      chatRequestStreamObserver.onCompleted();
//      latch.countDown();  // Decrease the latch count
//    };
//  }
//}
