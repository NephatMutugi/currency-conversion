package com.teohkenya.main.controller;

import com.teohkenya.main.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author NMuchiri
 **/
@RestController
@RequestMapping("/currency-conversion/")
public class CurrencyConversionController {


    private final CurrencyConversionService currencyConversionService;

    @Autowired
    public CurrencyConversionController(CurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }


    @GetMapping("from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity<?> convertCurrencies(@PathVariable(name = "from") String  from,
                                               @PathVariable(name = "to") String  to,
                                               @PathVariable(name = "quantity") String  quantity){

        return currencyConversionService.calculateCurrencyConversion(from, to,quantity);

    }

}
