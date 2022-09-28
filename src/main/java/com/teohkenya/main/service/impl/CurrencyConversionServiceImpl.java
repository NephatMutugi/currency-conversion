package com.teohkenya.main.service.impl;

import com.teohkenya.main.configs.CustomProperties;
import com.teohkenya.main.model.CurrencyConversion;
import com.teohkenya.main.model.ErrorDetails;
import com.teohkenya.main.model.Errors;
import com.teohkenya.main.service.CurrencyConversionService;
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

    @Autowired
    public CurrencyConversionServiceImpl(Utils utils, CustomProperties customProperties) {
        this.utils = utils;
        this.customProperties = customProperties;
    }

    @Override
    public ResponseEntity<?> calculateCurrencyConversion(String from, String to, Double quantity) {
        if (customProperties.getMock().equalsIgnoreCase("true")) {
            return utils.mockConversionService(from, to, quantity);
        } else {
            CurrencyConversion currencyConversion = new CurrencyConversion();

            ErrorDetails errorDetails = new ErrorDetails();
            Errors errors = new Errors();

//            currencyConversion.setTo(to);
//            currencyConversion.setFrom(from);
//            currencyConversion.setQuantity(quantity);

          try{
              // Make rest api call

              String url = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
              HashMap<String, String> uriVariables = new HashMap<>();

              uriVariables.put("from", from);
              uriVariables.put("to", to);

              ResponseEntity<CurrencyConversion> responseEntity =
                      new RestTemplate()
                              .getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                                      CurrencyConversion.class,
                                      uriVariables);

              currencyConversion = responseEntity.getBody();


              log.info("----------------------- RESPONSE ENTITY ---------------------");
              log.info(utils.formatObject(responseEntity));


              // If a null record was returned
              if (currencyConversion == null){

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

          } catch (NullPointerException ex){
              errors.setErrorCode("500");
              errors.setErrorMessage("Failure");
              errors.setErrorDetails(ex.getMessage());
              errorDetails.setErrors(errors);
              return new ResponseEntity<>(errorDetails, HttpStatus.OK);
          }
        }
    }
}
