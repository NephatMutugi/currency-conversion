package com.teohkenya.main.service;

import org.springframework.http.ResponseEntity;

/**
 * @ Author NMuchiri
 **/
public interface CurrencyConversionService {

    ResponseEntity<?> calculateCurrencyConversion(String from, String to, Double quantity);
}
