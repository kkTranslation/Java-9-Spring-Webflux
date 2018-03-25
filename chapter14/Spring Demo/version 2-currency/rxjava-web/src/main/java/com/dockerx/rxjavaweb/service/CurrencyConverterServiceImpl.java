package com.dockerx.rxjavaweb.service;

import com.dockerx.rxjavaweb.domain.dto.CurrencyRatesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import rx.Observable;

import java.util.Set;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/25 22:12.
 */
@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyConverterServiceImpl.class);
    @Value("${services.currency.uri}")
    private String CURRENCY_SERVICE_API;
    private static final String SYMBOLS = "symbols";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Observable<CurrencyRatesDTO> getCurrencyRates(Set<String> currencies,String base) {
        return getCurrencyRatesObservable(currencies,base);
    }

    private Observable<CurrencyRatesDTO> getCurrencyRatesObservable(Set<String> currencies,String base) {
        return Observable.<CurrencyRatesDTO>create(observer -> {
            CurrencyRatesDTO currencyRatesDTO =
                    restTemplate.getForEntity(UriComponentsBuilder.fromUriString(CURRENCY_SERVICE_API)
                                                                  .queryParam(SYMBOLS,
                                                                          currencies.toString()).queryParam("base",base)
                                                                  .toUriString(),
                            CurrencyRatesDTO.class)
                                .getBody();
            observer.onNext(currencyRatesDTO);
            observer.onCompleted();
        }).doOnNext(c -> logger.debug("Currency rates were retrieved successfully."))
          .doOnError(e -> logger.error("An ERROR occurred while retrieving the currency rates.", e));

    }
}
