package com.teohkenya.main.service.impl;

import com.teohkenya.main.configs.CustomProperties;
import com.teohkenya.main.model.CurrencyConversion;
import com.teohkenya.main.model.ErrorDetails;
import com.teohkenya.main.model.Errors;
import com.teohkenya.main.service.CurrencyConversionService;
import com.teohkenya.main.utils.CurrencyExchangeProxy;
import com.teohkenya.main.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @ Author NMuchiri
 **/
@Service
@Slf4j
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final Utils utils;
    private final CustomProperties customProperties;
    private final CurrencyExchangeProxy currencyExchangeProxy;

    @Autowired
    public CurrencyConversionServiceImpl(Utils utils, CustomProperties customProperties, CurrencyExchangeProxy currencyExchangeProxy) {
        this.utils = utils;
        this.customProperties = customProperties;
        this.currencyExchangeProxy = currencyExchangeProxy;
    }


    // Using feign client to send http requests
    @Override
    public ResponseEntity<?> calculateCurrencyConversion(String from, String to, Double quantity) {
        if (customProperties.getMock().equalsIgnoreCase("true")) {
            return utils.mockConversionService(from, to, quantity);
        } else {
            CurrencyConversion currencyConversion = new CurrencyConversion();

            ErrorDetails errorDetails = new ErrorDetails();
            Errors errors = new Errors();


            try {
                // Make rest api call

                String url = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
                HashMap<String, String> uriVariables = new HashMap<>();

                uriVariables.put("from", from);
                uriVariables.put("to", to);

                ResponseEntity<CurrencyConversion> responseEntity =
                        new RestTemplate()
                                .getForEntity(url,
                                        CurrencyConversion.class,
                                        uriVariables);

                currencyConversion = responseEntity.getBody();


                log.info("----------------------- RESPONSE ENTITY ---------------------");
                log.info(utils.formatObject(responseEntity));


                // If a null record was returned
                if (currencyConversion == null) {

                    errors.setErrorCode("500");
                    errors.setErrorMessage("Failure");
                    errors.setErrorDetails("An Error Occurred");
                    errorDetails.setErrors(errors);
                    return new ResponseEntity<>(errorDetails, HttpStatus.OK);
                }

                Double conversionMultiple = currencyConversion.getConversionMultiple();

                CurrencyConversion convertCurrencies = new CurrencyConversion(currencyConversion.getId(),
                        from, to, quantity,
                        conversionMultiple,
                        (quantity * conversionMultiple),
                        currencyConversion.getEnvironment());

                return new ResponseEntity<>(convertCurrencies, HttpStatus.OK);

            } catch (NullPointerException ex) {
                errors.setErrorCode("500");
                errors.setErrorMessage("Failure");
                errors.setErrorDetails(ex.getMessage());
                errorDetails.setErrors(errors);
                return new ResponseEntity<>(errorDetails, HttpStatus.OK);
            }
        }
    }


    // Using feign client to send http requests
    @Override
    public ResponseEntity<?> calculateCurrencyConversionFeign(String from, String to, Double quantity) {

        ResponseEntity<CurrencyConversion> responseEntity = currencyExchangeProxy.currencyExchange(from, to);

        log.info("----------------------- RESPONSE ENTITY ---------------------");
        log.info(utils.formatObject(responseEntity));

        CurrencyConversion currencyConversion = responseEntity.getBody();

        if (currencyConversion != null) {
            currencyConversion.setQuantity(quantity);
            currencyConversion.setTotalCalculatedAmount(currencyConversion.getConversionMultiple() * quantity);

            String environment = currencyConversion.getEnvironment();
            currencyConversion.setEnvironment("Port: " + environment + ", Client: Feign Client");
        }


        return new ResponseEntity<>(currencyConversion, HttpStatus.OK);
    }
}
