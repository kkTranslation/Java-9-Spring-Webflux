package com.dockerx.reactive.orderstock;

import com.dockerx.reactive.orderstock.product.Order;
import com.dockerx.reactive.orderstock.product.OrderItem;
import com.dockerx.reactive.orderstock.product.Product;
import com.dockerx.reactive.orderstock.stock.Stock;
import com.dockerx.reactive.orderstock.stock.StockMaintain;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/12/23 2:10.
 */
public class TestStockMaintain {
    private static final Logger log = LoggerFactory.getLogger(TestStockMaintain.class);
    @Test
    public void teststockRemoval() throws InterruptedException {
        Stock stock = new Stock();
        SubmissionPublisher<Order> p = new SubmissionPublisher<>();
        p.subscribe(new StockMaintain(stock));
        Product product = new Product();
        stock.store(product, 40);
        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setAmount(10);
        Order order = new Order();
        List<OrderItem> items = new LinkedList<>();
        items.add(item);
        order.setItems(items);
        for (int i = 0; i < 10; i++)
            p.submit(order);
        log.info("所有订单已经提交完毕");
        for (int j = 0; j < 10; j++) {
            log.info("Sleeping a bit...");
            Thread.sleep(50);
        }
        p.close();
        log.info("Publisher已关闭");
    }
}
