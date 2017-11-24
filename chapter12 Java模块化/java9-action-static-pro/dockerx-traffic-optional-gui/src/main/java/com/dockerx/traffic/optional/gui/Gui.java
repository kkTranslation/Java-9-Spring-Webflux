package com.dockerx.traffic.optional.gui;

import com.dockerx.traffic.optional.entity.Person;
import com.dockerx.traffic.optional.three.Student;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/21 13:37.
 */
public class Gui {
    public static void main(String[] args) {
        // module-info.java 文件中使用static修饰后,会在编译阶段直接忽略这个类，不会加载到文件系统中，
        // 而当其他依赖模块中比如(dockerx.traffic.optional.three模块)中存在此模块所忽略的模块，
        // 那么，Jrtfs依然会加载到其文件系统中，于是就可以在运行时正常使用此module-info文件中所忽略的模块中的类了
        Person person = new Person(100, 1);
        System.out.println(Person.class.getAnnotations().length);

        if (Bike.class.getAnnotations().length > 0) {
            System.out.println("Running with annotation @Anno present.");
        } else {
            System.out.println("Running without annotation @Anno present.");
        }

        Student student = new Student();
        // 同样，使用反射也是可以的
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
