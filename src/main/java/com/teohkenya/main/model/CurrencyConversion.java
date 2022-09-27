package com.teohkenya.main.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @ Author NMuchiri
 **/
@Getter
@Setter
public class CurrencyConversion {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("conversionMultiple")
    private BigDecimal conversionMultiple;

    @JsonProperty("quantity")
    private BigDecimal quantity;

    @JsonProperty("totalCalculatedAmount")
    private BigDecimal totalCalculatedAmount;

    @JsonProperty("environment")
    private String environment;
}
