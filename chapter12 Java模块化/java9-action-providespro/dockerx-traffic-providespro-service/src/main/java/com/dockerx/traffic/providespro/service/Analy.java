package com.dockerx.traffic.providespro.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/20 23:28.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Analy {
    public boolean value() default true;
}
