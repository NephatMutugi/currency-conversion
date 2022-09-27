package com.teohkenya.main.service.impl;

import com.teohkenya.main.service.CurrencyConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @ Author NMuchiri
 **/
@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {
    @Override
    public ResponseEntity<?> calculateCurrencyConversion(String from, String to, String quantity) {
        return null;
    }
}
