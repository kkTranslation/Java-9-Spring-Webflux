package com.dockerx.reactive.orderstock.product;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/12/23 1:54.
 */
public class OrderItem {
    private long amount;
    private Product product;

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getAmount() {
        return amount;
    }

    public Product getProduct() {
        return product;
    }
}
