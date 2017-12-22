package com.dockerx.reactive.orderstock.product;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/12/22 22:34.
 */
public class ProductIsOutOfStock extends Exception {
    public ProductIsOutOfStock(Product product) {
        super(product.toString());
    }
}
