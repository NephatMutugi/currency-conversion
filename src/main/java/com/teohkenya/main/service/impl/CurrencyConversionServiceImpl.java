package com.teohkenya.main.service.impl;

import com.teohkenya.main.service.CurrencyConversionService;
import com.teohkenya.main.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @ Author NMuchiri
 **/
@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final Utils utils;

    @Autowired
    public CurrencyConversionServiceImpl(Utils utils) {
        this.utils = utils;
    }

    @Override
    public ResponseEntity<?> calculateCurrencyConversion(String from, String to, Double quantity) {
        return utils.mockConversionService(from, to, quantity);
    }
}
