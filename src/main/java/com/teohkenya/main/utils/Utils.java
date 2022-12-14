package com.teohkenya.main.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teohkenya.main.model.CurrencyConversion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @ Author NMuchiri
 **/
@Component
public class Utils {

    public ResponseEntity<CurrencyConversion> mockConversionService(String to, String from, Double quantity) {
        CurrencyConversion currencyConversion = new CurrencyConversion();
        currencyConversion.setId(1001L);
        currencyConversion.setTo(to);
        currencyConversion.setFrom(from);
        currencyConversion.setQuantity(quantity);
        currencyConversion.setConversionMultiple(120.00);
        currencyConversion.setTotalCalculatedAmount(quantity * 120);

        return new ResponseEntity<>(currencyConversion, HttpStatus.OK);
    }

    public String formatObject(Object entity) {

        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(entity);

        } catch (JsonProcessingException e) {

            return e.getMessage();
        }
    }
}
