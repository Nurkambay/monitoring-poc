package com.monitoring.receiver.api.transport;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix = "aws")
@Data
@Configuration
public class AwsProperties {
  private String accessKey;
  private String secretKey;
  private String clientRegion;
}
