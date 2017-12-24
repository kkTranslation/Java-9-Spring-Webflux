package com.dockerx.reactive.jdkaction;

import java.util.concurrent.Flow;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/12/21 22:02.
 */
public class DockerXDemoSubscriber<T> implements Flow.Subscriber<T>{
    private String name;
    private Flow.Subscription subscription;
    final long bufferSize;
    long count;

    public String getName() {
        return name;
    }

    public Flow.Subscription getSubscription() {
        return subscription;
    }

    public DockerXDemoSubscriber(long bufferSize, String name) {
        this.bufferSize = bufferSize;
        this.name = name;
    }

    public void onSubscribe(Flow.Subscription subscription) {
        //count = bufferSize - bufferSize / 2;// 当消费一半的时候重新请求
        (this.subscription = subscription).request(bufferSize);
        System.out.println("开始onSubscribe订阅");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onNext(T item) {
        //if (--count <= 0) subscription.request(count = bufferSize - bufferSize / 2);
        System.out.println(" ############# " + Thread.currentThread().getName()+"　name: "+name+"　item: "+item+ " ############# ");
        System.out.println(name + " received: " + item);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    public void onComplete() {
        System.out.println("Completed");
    }
}
