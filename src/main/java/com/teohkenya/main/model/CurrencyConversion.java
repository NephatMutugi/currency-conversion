package com.teohkenya.main.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @ Author NMuchiri
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConversion {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("quantity")
    private Double quantity;

    @JsonProperty("conversionMultiple")
    private Double conversionMultiple;

    @JsonProperty("totalCalculatedAmount")
    private Double totalCalculatedAmount;

    @JsonProperty("environment")
    private String environment;
}
