package com.dockerx.traffic.factory.serviceimpl.a;

import com.dockerx.traffic.factory.entity.Person;
import com.dockerx.traffic.factory.service.Analysis;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/20 22:16.
 */
public class A_Analysis implements Analysis {

    public static final String NAME = "A_Analysis";

    @Override
    public String analyze(Person person) {
        int mileage = person.getMileage();
        int money = person.getMoney();
        if (mileage<=0) return "别闹";
        return mileage<1000?"步行"
                :mileage<10000&&money>=10?"单车"
                :mileage>=10000&&money>=25?"专车"
                :money>=10?"单车":"老实步行吧";
    }

    @Override
    public String getName() {
        return NAME;
    }
}
