package com.dockerx.reactive.submissionPublisher;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.LongStream;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/12/27 0:33.
 */
public class ConsumeSubmissionPublisher {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        publish();
    }
    public static void publish()throws InterruptedException, ExecutionException
    {
        CompletableFuture future = null;
        try (SubmissionPublisher publisher = new SubmissionPublisher<Long>()) {
            System.out.println("Subscriber Buffer Size: " + publisher.getMaxBufferCapacity());
            future=publisher.consume(System.out::println);
            LongStream.range(1, 10).forEach(publisher::submit);
        } finally {
            future.get();
        }
    }
}
