package com.monitoring.receiver.api;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration
 */
@EnableSwagger2
@Configuration
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurerAdapter {
  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
        .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.cloud")))
        .apis(Predicates
                  .not(RequestHandlerSelectors.basePackage("org.springframework.data.rest.webmvc")))
        .paths(PathSelectors.any())

        .build();

  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/swagger-ui.html**")
        .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}
