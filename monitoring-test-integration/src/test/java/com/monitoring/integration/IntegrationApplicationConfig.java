package com.monitoring.integration;

import com.monitoring.model.entities.CustomerRepository;
import com.monitoring.model.entities.VehicleRepository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = {"com.monitoring.integration", "com.monitoring.service", "com.monitoring.service.*"})
@EnableConfigurationProperties
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
@EnableJpaRepositories(basePackageClasses = {CustomerRepository.class, VehicleRepository.class})
@EntityScan(basePackages = "com.monitoring.model.*")
@EnableAutoConfiguration(exclude = {
    JpaRepositoriesAutoConfiguration.class
})
public class IntegrationApplicationConfig {
}
