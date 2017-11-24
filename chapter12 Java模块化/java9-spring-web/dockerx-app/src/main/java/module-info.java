/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/15 19:21.
 */
module dockerx.app {
    requires spring.context;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires dockerx.user;
    requires dockerx.admin;
    requires spring.beans;
    requires spring.web;
}