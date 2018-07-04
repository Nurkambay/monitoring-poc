package com.monitoring.service;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@EntityScan(basePackages = "com.monitoring.model.*")
public class TestServiceApplicationConfig {
}
