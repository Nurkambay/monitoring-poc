package com.monitoring.receiver.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.monitoring.receiver.api", "com.monitoring.receiver.api.*"})
@EnableConfigurationProperties
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
public class ReceiverApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    new SpringApplicationBuilder(
        ReceiverApplication.class).web(true).run(args);
  }

}
