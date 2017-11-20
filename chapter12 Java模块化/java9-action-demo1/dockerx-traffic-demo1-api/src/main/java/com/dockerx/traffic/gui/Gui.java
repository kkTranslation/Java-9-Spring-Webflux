package com.dockerx.traffic.gui;

import com.dockerx.traffic.entity.Person;
import com.dockerx.traffic.service.Analysis;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/20 20:35.
 */
public class Gui {
    public static void main(String[] args) {
        Person nao = new Person(0, 0);
        Person miniperson = new Person(10, 0);
        Person minperson = new Person(1001, 0);
        Person minxperson = new Person(1001, 11);
        Person maxperson = new Person(100000, 11);
        Person maxxperson = new Person(100000, 26);
        Person poorperson = new Person(100000, 0);

        Analysis analysis = new Analysis();
        System.out.println(analysis.analyze(nao));
        System.out.println(analysis.analyze(miniperson));
        System.out.println(analysis.analyze(minperson));
        System.out.println(analysis.analyze(minxperson));
        System.out.println(analysis.analyze(maxperson));
        System.out.println(analysis.analyze(maxxperson));
        System.out.println(analysis.analyze(poorperson));
    }
}
