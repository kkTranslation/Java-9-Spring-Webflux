package com.dockerx.reactive.orderstock.stock;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 库存商品数量操作类
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/12/22 21:36.
 */
public class StockItem {
    private final AtomicLong amountItemStock =
            new AtomicLong(0);

    public void store(long n) {
        amountItemStock.accumulateAndGet(n, (pre, mount) -> pre + mount);
    }
    // 下订单时，没超过库存量的话就减去，超过的话，库存不做任何操作，返回的就是下了库存移除的具体数量。
    public long remove(long n) {
        class RemoveData {
            long remove;
        }
        RemoveData removeData = new RemoveData();
        amountItemStock.accumulateAndGet(n,
                (pre, mount) -> pre >= n ? pre - (removeData.remove = mount) : pre - (removeData.remove = 0L)
        );
        return removeData.remove;
    }
}
