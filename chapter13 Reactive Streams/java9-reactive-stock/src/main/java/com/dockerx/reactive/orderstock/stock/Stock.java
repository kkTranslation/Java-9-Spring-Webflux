package com.dockerx.reactive.orderstock.stock;

import com.dockerx.reactive.orderstock.product.Product;
import com.dockerx.reactive.orderstock.product.ProductIsOutOfStock;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 库存操作类
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/12/22 22:12.
 */
@Component
public class Stock {
    private final Map<Product,StockItem> stockItemMap = new ConcurrentHashMap<>();

    private StockItem getItem(Product product){
        //如果没有此商品，添加一个这个key，返回给null就是
        stockItemMap.putIfAbsent(product,new StockItem());
        return stockItemMap.get(product);
    }

    public void store(Product product,long amount){
        getItem(product).store(amount);
    }

    public void remove(Product product,long amount)  throws ProductIsOutOfStock {
        if (getItem(product).remove(amount) != amount)
            throw new ProductIsOutOfStock(product);
    }
}
