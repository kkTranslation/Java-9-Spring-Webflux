package com.dockerx.traffic.providespro.gui;


import com.dockerx.traffic.providespro.entity.Person;
import com.dockerx.traffic.providespro.service.Analy;
import com.dockerx.traffic.providespro.service.Analysis;

import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/20 20:35.
 */
public class Gui {
    public static void main(String[] args) {
        ServiceLoader<Analysis> analyses = ServiceLoader.load(Analysis.class);
      //  analyses.stream().map(ServiceLoader.Provider::get).forEach(analyzer -> System.out.println(analyzer.getName()));
        Set<Analysis> collect = analyses.stream()
                                        .filter(p -> isAnno(p.type()))
                                        .map(ServiceLoader.Provider::get).collect(Collectors.toSet());

        Person nao = new Person(0, 0);
        Person miniperson = new Person(10, 0);
        Person minperson = new Person(1001, 0);
        Person minxperson = new Person(1001, 11);
        Person maxperson = new Person(100000, 11);
        Person maxxperson = new Person(100000, 26);
        Person poorperson = new Person(100000, 0);

        collect.forEach(p->System.out.println(p.analyze(maxxperson)));


    }

    private static boolean isAnno(Class<?> clazz) {
        return clazz.isAnnotationPresent(Analy.class)
                && clazz.getAnnotation(Analy.class).value();

    }
}
