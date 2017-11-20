package com.dockerx.traffic.factory;

import com.dockerx.traffic.factory.service.Analysis;
import com.dockerx.traffic.factory.serviceimpl.a.A_Analysis;
import com.dockerx.traffic.factory.serviceimpl.b.B_Analysis;

import java.util.List;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/20 22:22.
 */
public class AnalyzerFactory {

    public static List<String> getSupportedAnalyses() {
        return List.of(A_Analysis.NAME, B_Analysis.NAME);
    }

    public static Analysis getAnalyzer(String name) {
        switch (name) {
            case A_Analysis.NAME: return new A_Analysis();
            case B_Analysis.NAME: return new B_Analysis();
            default: throw new IllegalArgumentException("No such analyzer!");
        }
    }
}
