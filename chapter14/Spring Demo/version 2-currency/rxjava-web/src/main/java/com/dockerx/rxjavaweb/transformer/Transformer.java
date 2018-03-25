package com.dockerx.rxjavaweb.transformer;

/**
 * 将获取到的数据转化为实例对象
 *
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/23 18:02.
 */
public interface Transformer<T,R> {
    R transform(T source);
}
