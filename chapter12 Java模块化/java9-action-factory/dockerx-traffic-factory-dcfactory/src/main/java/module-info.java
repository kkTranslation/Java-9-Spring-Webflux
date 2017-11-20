/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/20 22:25.
 */
module dockerx.traffic.factory.dcfactory {
    requires dockerx.traffic.factory.service;
    requires dockerx.traffic.factory.serviceimpl.a;
    requires dockerx.traffic.factory.serviceimpl.b;
    exports com.dockerx.traffic.factory;
}