package com.dockerx.traffic.factory.gui;


import com.dockerx.traffic.factory.AnalyzerFactory;
import com.dockerx.traffic.factory.entity.Person;

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

        for(String name: AnalyzerFactory.getSupportedAnalyses()) {
            System.out.println(name + ": " + AnalyzerFactory.getAnalyzer(name).analyze(nao));
            System.out.println(name + ": " + AnalyzerFactory.getAnalyzer(name).analyze(miniperson));
            System.out.println(name + ": " + AnalyzerFactory.getAnalyzer(name).analyze(minperson));
            System.out.println(name + ": " + AnalyzerFactory.getAnalyzer(name).analyze(minxperson));
            System.out.println(name + ": " + AnalyzerFactory.getAnalyzer(name).analyze(maxperson));
            System.out.println(name + ": " + AnalyzerFactory.getAnalyzer(name).analyze(maxxperson));
            System.out.println(name + ": " + AnalyzerFactory.getAnalyzer(name).analyze(poorperson));
            System.out.println("##########################");
        }


    }
}
