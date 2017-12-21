package com.dockerx.reactive.jdkaction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/12/21 22:01.
 */
public class DockerXDemoSubscription<T> implements Flow.Subscription{
    private final Flow.Subscriber<T> subscriber;
    private final ExecutorService executor;
    private Future<?> future;
    private T item;
    private boolean completed;

    public DockerXDemoSubscription(Flow.Subscriber<T> subscriber, ExecutorService executor) {
        this.subscriber = subscriber;
        this.executor = executor;
    }

    @Override
    public void request(long n) {
        if (n != 0 && !completed) {
          if (n < 0) {
            IllegalArgumentException ex = new IllegalArgumentException();
            executor.execute(() -> subscriber.onError(ex));
          } else {
           future = executor.submit(() -> {
              subscriber.onNext(item);
            });
          }
        } else {
            subscriber.onComplete();
        }
    }

    @Override
    public void cancel() {
        completed = true;
        if (future != null && !future.isCancelled()) {
            this.future.cancel(true);
        }
    }
}
