package com.dockerx.traffic.providespro.entity;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/20 20:11.
 */
public class Person {
    private int mileage;
    private int money;

    public Person(int mileage, int money) {
        this.mileage = mileage;
        this.money = money;
    }

    public int getMileage() {
        return mileage;
    }

    public Person setMileage(int mileage) {
        this.mileage = mileage;
        return this;
    }

    public int getMoney() {
        return money;
    }

    public Person setMoney(int money) {
        this.money = money;
        return this;
    }
}
