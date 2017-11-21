/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/21 1:13.
 */
module dockerx.traffic.providespro.serviceimpla {
    requires dockerx.traffic.providespro.service;
    requires dockerx.traffic.providespro.entity;
    provides com.dockerx.traffic.providespro.service.Analysis
            with com.dockerx.traffic.providespro.serviceimpl.a.A_Analysis;
}