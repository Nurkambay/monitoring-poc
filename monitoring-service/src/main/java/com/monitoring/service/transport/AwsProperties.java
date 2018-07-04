package com.monitoring.service.transport;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Amazon web service properties
 */
@ConfigurationProperties(prefix = "aws")
@Data
@Configuration
public class AwsProperties {
  private String accessKey;
  private String secretKey;
  private String clientRegion;
}
