package com.dockerx.rxjavaweb.service;

import com.dockerx.rxjavaweb.domain.dto.CurrencyRatesDTO;
import rx.Observable;

import java.util.Set;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/25 22:10.
 */

public interface CurrencyConverterService {
    Observable<CurrencyRatesDTO> getCurrencyRates(Set<String> currencies,String base);
}
