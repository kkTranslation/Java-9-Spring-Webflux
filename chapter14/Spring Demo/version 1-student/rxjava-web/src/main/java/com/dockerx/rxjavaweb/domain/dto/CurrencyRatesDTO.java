package com.dockerx.rxjavaweb.domain.dto;

import java.util.Map;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/25 22:09.
 */
public class CurrencyRatesDTO {
    private String base;
    private String date;
    private Map<String, Double> rates;

    public String getBase() {
        return base;
    }

    public CurrencyRatesDTO setBase(String base) {
        this.base = base;
        return this;
    }

    public String getDate() {
        return date;
    }

    public CurrencyRatesDTO setDate(String date) {
        this.date = date;
        return this;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public CurrencyRatesDTO setRates(Map<String, Double> rates) {
        this.rates = rates;
        return this;
    }
}
