package com.dockerx.rxjavaweb.controller;

import com.dockerx.rxjavaweb.domain.dto.CurrencyRatesDTO;
import com.dockerx.rxjavaweb.service.CurrencyConverterService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rx.Observable;

import java.util.Set;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/25 22:32.
 */
@RestController
@RequestMapping("/api/currencyconverter")
public class CurrencyController {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    @Autowired
    private  CurrencyConverterService currencyConverterService;

    @ApiOperation(value="汇率查询", notes="根据想查的币种来查找与指定基础货币对比币种对应的汇率")
    @ApiImplicitParam(name = "symbols", value = "所要查询的汇率的币种集合", required = true)
    @GetMapping(value = "/rates",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Observable<CurrencyRatesDTO> getCurrencyRates(@RequestParam("symbols") Set<String> symbols,@RequestParam("base")String base) {
        logger.debug("Retrieving currency rates.");
        return currencyConverterService.getCurrencyRates(symbols,base);
    }

}
