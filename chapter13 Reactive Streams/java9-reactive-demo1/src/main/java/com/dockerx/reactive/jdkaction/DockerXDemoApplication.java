package com.dockerx.reactive.jdkaction;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/12/21 22:54.
 */
public class DockerXDemoApplication {

    public static void main(String[] args) {
        Flow_customsubmissionPublisher();
    }

    private static void demoSubscribe(DockerXDemoPublisher<Integer> publisher, String subscriberName){
        DockerXDemoSubscriber<Integer> subscriber = new DockerXDemoSubscriber<>(4L,subscriberName);

        publisher.subscribe(subscriber);
    }

    private static void Flow_customsubmissionPublisher() {
        ExecutorService execService =  ForkJoinPool.commonPool();//Executors.newFixedThreadPool(3);
        try (DockerXDemoPublisher<Integer> publisher = new  DockerXDemoPublisher<>(execService)){
            demoSubscribe(publisher,  "One");
            demoSubscribe(publisher,  "Two");
            demoSubscribe(publisher,  "Three");
            IntStream.range(1, 5).forEach(publisher::submit);
        } finally {
            try {
                execService.shutdown();
                int shutdownDelaySec = 1;
                System.out.println("………………等待 " + shutdownDelaySec + " 秒后结束服务……………… ");
                execService.awaitTermination(shutdownDelaySec, TimeUnit.SECONDS);
            } catch (Exception ex) {
                System.out.println("捕获到 execService.awaitTermination()方法的异常: " + ex.getClass().getName());
            } finally {
                System.out.println("调用 execService.shutdownNow()结束服务...");
                List<Runnable> l = execService.shutdownNow();
                System.out.println("还剩 "+l.size() + " 个任务等待被执行，服务已关闭 ");
            }

        }
    }
}
