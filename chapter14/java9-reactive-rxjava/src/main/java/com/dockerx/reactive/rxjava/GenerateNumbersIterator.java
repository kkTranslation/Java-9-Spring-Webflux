package com.dockerx.reactive.rxjava;

import java.math.BigInteger;
import java.util.Iterator;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/12/28 0:34.
 */
public class GenerateNumbersIterator implements Iterator{
    private BigInteger current = BigInteger.ZERO;
    private BigInteger num;

    public GenerateNumbersIterator(BigInteger num) {
        this.num = num;
    }

    @Override
    public boolean hasNext() {
        return current.compareTo(num)<0;
    }

    @Override
    public Object next() {
        current = current.add(BigInteger.ONE);
        return current;
    }

    public static void main(String[] args) {
        GenerateNumbersIterator generateNumbersIterator=new GenerateNumbersIterator(BigInteger.valueOf(10L));
        while (generateNumbersIterator.hasNext()){
            System.out.println(generateNumbersIterator.next());
        }
    }
}
