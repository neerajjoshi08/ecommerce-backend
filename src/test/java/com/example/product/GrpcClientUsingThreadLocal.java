//package com.example.product;
//
//import com.example.HelloServiceGrpc;
//import com.example.HelloServiceOuterClass.ChatRequest;
//import com.example.HelloServiceOuterClass.ChatResponse;
//import io.grpc.Channel;
//import io.grpc.ManagedChannelBuilder;
//import io.grpc.stub.StreamObserver;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.LinkedBlockingQueue;
//import org.junit.jupiter.api.Test;
//
//class GrpcStreamClient {
//
//  private final HelloServiceGrpc.HelloServiceStub stub;
//  private final CountDownLatch latch = new CountDownLatch(1);
//  private final ThreadLocal<StreamObserver<ChatRequest>> requestsObservers = ThreadLocal.withInitial(
//      this::newStream);
//
//  public GrpcStreamClient(String host, int port) {
//    Channel channel = ManagedChannelBuilder.forAddress(host, port)
//        .usePlaintext()
//        .build();
//    this.stub = HelloServiceGrpc.newStub(channel);
//  }
//
//  private StreamObserver<ChatRequest> newStream() {
//    StreamObserver<ChatResponse> responseObserver = new StreamObserver<>() {
//      final StringBuffer buffer = new StringBuffer();
//
//      @Override
//      public void onNext(ChatResponse value) {
//        String result = value.getResult();
//        System.out.println("Received value: " + result);
//        buffer.append(result);
//        if (result.endsWith("EOF")) {
//          String full = buffer.toString().replace("EOF", "");
//          latch.countDown();
//          // play
//          buffer.setLength(0);
//        }
//      }
//
//      @Override
//      public void onError(Throwable t) {
//        requestsObservers.remove();
//        buffer.setLength(0);
//
//      }
//
//      @Override
//      public void onCompleted() {
//        requestsObservers.remove();
//        buffer.setLength(0);
//      }
//    };
//    return stub.chat(responseObserver);
//  }
//
//  void sendData(ChatRequest chatRequest) {
//    StreamObserver<ChatRequest> o = requestsObservers.get();
/// /    o.onCompleted();
//    o.onNext(chatRequest);
//
//    try {
//      latch.await();
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//  }
//}
//
//
//class GrpcClientUsingThreadLocalTest {
//
//  @Test
//  public void test_using_thread_locals() {
//    GrpcStreamClient client = new GrpcStreamClient("localhost", 50051);
//    client.sendData(ChatRequest.newBuilder()
//        .setMessage("Hello-")
//        .build());
//
//
//
//  }
//
//  @Test
//  public void test_interrupt_thread() {
//    BlockingQueue<Object> blockingQueue = new LinkedBlockingQueue<>();
//    try {
//      blockingQueue.take();
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//    Runnable runnable = () -> {
//      try {
//        while (true) {
//          System.out.println("Doing something");
//          Thread.dumpStack();
//          Thread.currentThread().interrupt();
//        }
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
//    };
//
//    Thread thread = new Thread(runnable);
//    thread.start();
/// /    thread.interrupt();
//  }
//
////  LinkedBlockingQueue
//}
