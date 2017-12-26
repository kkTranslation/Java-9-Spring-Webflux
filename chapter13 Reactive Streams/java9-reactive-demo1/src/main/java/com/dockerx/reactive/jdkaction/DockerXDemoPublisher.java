package com.dockerx.reactive.jdkaction;

import java.util.concurrent.*;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/12/24 23:28.
 */
public class DockerXDemoPublisher<T> implements Flow.Publisher<T>, AutoCloseable {
    private final ExecutorService executor; // daemon-based
    private CopyOnWriteArrayList<DockerXDemoSubscription> list = new CopyOnWriteArrayList();


    public void submit(T item) {
        System.out.println("***************** 开始发布元素 item: "+item+" *****************");
        list.forEach(e -> {
            e.future=executor.submit(() -> { e.subscriber.onNext(item);});

        });
    }

    public DockerXDemoPublisher(ExecutorService executor) {
        this.executor = executor;
    }

    public void close() {
        list.forEach(e -> {
            e.future=executor.submit(() -> { e.subscriber.onComplete();});
        });
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new DockerXDemoSubscription(subscriber, executor));
        list.add(new DockerXDemoSubscription(subscriber, executor));

    }

    static class DockerXDemoSubscription<T> implements Flow.Subscription {
        private final Flow.Subscriber<? super T> subscriber;
        private final ExecutorService executor;
        private Future<?> future;
        private T item;
        private boolean completed;


        public DockerXDemoSubscription(Flow.Subscriber<? super T> subscriber, ExecutorService executor) {
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
}
