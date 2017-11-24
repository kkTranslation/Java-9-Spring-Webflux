package com.dockerx.traffic.optional.gui;

import com.dockerx.traffic.optional.entity.Person;

import java.lang.annotation.Annotation;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/21 13:37.
 */
public class Gui {
    public static void main(String[] args) {
        // module-info.java 文件中使用static修饰后,会在编译阶段直接忽略这个类，不会加载到文件系统中，
        // 下面注释掉的两条语句都会报ClassNotFoundException
        // Person person = new Person(100, 1);
        // System.out.println(Person.class.getAnnotations().length);

        if (Bike.class.getAnnotations().length > 0) {
            System.out.println("Running with annotation @Anno present.");
        } else {
            System.out.println("Running without annotation @Anno present.");
        }
        // 同样，使用反射也是不行的，会报下面catch到的错了
        try {
            Class<?> clazz = Class.forName("com.dockerx.traffic.optional.entity.Person");
            Person instance =
                    (Person) clazz.getConstructor().newInstance();
            instance.setMileage(10);
            System.out.println("Using Person");
        } catch (ReflectiveOperationException e) {
            System.out.println("Oops, we need a fallback!");
        }
    }
}