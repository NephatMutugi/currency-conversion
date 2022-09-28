package com.teohkenya.main.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ Author NMuchiri
 **/
@Configuration
@ConfigurationProperties(prefix = "conversion")
@Getter
@Setter
public class CustomProperties {

    private String mock;
}
