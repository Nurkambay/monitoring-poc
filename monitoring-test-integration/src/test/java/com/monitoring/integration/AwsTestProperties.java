package com.monitoring.integration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Amazon web service properties
 */
@ConfigurationProperties(prefix = "aws")
@Data
@Configuration
public class AwsTestProperties {
  private String accessKey;
  private String secretKey;
  private String clientRegion;
}
