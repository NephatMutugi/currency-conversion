package com.teohkenya.main.utils;

import com.teohkenya.main.model.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ Author NMuchiri
 **/
@Component
@FeignClient(name = "currency-exchange", url = "localhost:8000/currency-exchange")
public interface CurrencyExchangeProxy {

    @GetMapping("/from/{from}/to/{to}")
    ResponseEntity<CurrencyConversion> currencyExchange(@PathVariable(value = "from") String from,
                                                        @PathVariable(value = "to") String to);

}
