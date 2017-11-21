package com.dockerx.traffic.optional.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/21 13:30.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={TYPE})
public @interface Anno {
    boolean value() default true;
}
