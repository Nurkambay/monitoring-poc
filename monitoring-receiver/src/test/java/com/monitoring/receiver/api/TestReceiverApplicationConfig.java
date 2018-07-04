package com.monitoring.receiver.api;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = {"com.monitoring.receiver.api.*"})
public class TestReceiverApplicationConfig {

}
