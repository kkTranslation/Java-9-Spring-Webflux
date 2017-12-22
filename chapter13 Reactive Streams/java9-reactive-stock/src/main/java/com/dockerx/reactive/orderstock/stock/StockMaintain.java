package com.dockerx.reactive.orderstock.stock;


import com.dockerx.reactive.orderstock.product.Order;
import com.dockerx.reactive.orderstock.product.ProductIsOutOfStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.ForkJoinPool;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/12/23 1:38.
 */
public class StockMaintain implements Flow.Subscriber<Order> {

    private static final Logger log = LoggerFactory.getLogger(StockMaintain.class);

    private Stock stock;
    private Flow.Subscription subscription = null;
    private ExecutorService execService =  ForkJoinPool.commonPool();

    public StockMaintain(@Autowired Stock stock) {
        this.stock = stock;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        log.info("******调用 onSubscribe******");
        subscription.request(3);
        this.subscription = subscription;
    }

    @Override
    public void onNext(Order order) {
        execService.submit(() -> {
            log.info("Thread {}", Thread.currentThread().getName());
            order.getItems().forEach(item -> {
                try {
                    stock.remove(item.getProduct(),item.getAmount());
                    log.info("有 {} 件产品从库存消耗 ",item.getAmount());
                } catch (ProductIsOutOfStock productIsOutOfStock) {
                    log.error("产品库存不足");
                }
            });
            subscription.request(1);
        });
    }

    @Override
    public void onError(Throwable throwable) {
        log.info(" 因为 {} 调用 onError ", throwable);
    }

    @Override
    public void onComplete() {
        log.info(" 调用 onComplete ");
    }
}
