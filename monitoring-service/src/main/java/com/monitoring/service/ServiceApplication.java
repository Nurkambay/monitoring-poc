package com.monitoring.service;

import com.monitoring.model.entities.CustomerRepository;
import com.monitoring.model.entities.VehicleRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.monitoring.service", "com.monitoring.service.*"})
@EnableConfigurationProperties
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
@EnableJpaRepositories(basePackageClasses = {CustomerRepository.class, VehicleRepository.class})
@EntityScan(basePackages = "com.monitoring.model.*")
public class ServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(ServiceApplication.class, args);
  }
}
