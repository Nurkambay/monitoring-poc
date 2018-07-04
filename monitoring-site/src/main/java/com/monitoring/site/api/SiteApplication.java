package com.monitoring.site.api;

import com.monitoring.model.entities.CustomerRepository;
import com.monitoring.model.entities.VehicleRepository;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.monitoring.site.api", "com.monitoring.site.api.*"})
@EnableConfigurationProperties
@EnableJpaRepositories(basePackageClasses = {CustomerRepository.class, VehicleRepository.class})
@EntityScan(basePackages = "com.monitoring.model.*")
public class SiteApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    new SpringApplicationBuilder(
        SiteApplication.class).web(true).run(args);
  }

}
