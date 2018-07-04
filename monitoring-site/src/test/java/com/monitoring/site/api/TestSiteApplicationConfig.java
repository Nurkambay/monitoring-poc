package com.monitoring.site.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

@Configuration
@ComponentScan(basePackages = {"com.monitoring.site.api.converter", "com.monitoring.site.api.model"})
@TestPropertySource(properties = {
    "vehicle.timeout=60",
})
public class TestSiteApplicationConfig {

}
