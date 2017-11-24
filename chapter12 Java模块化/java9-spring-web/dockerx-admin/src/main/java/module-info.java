/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/15 20:38.
 */
open module  dockerx.admin {
    requires spring.context;
    requires spring.web;
    requires spring.beans;

    exports com.dockerx.admin.model;
    exports com.dockerx.admin.service;
    exports com.dockerx.admin.configration;
}