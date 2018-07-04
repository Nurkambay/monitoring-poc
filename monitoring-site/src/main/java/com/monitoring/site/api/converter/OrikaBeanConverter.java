package com.monitoring.site.api.converter;

import ma.glasnost.orika.Converter;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Converter for converting DB Entities to data model representation
 */
@Component
public class OrikaBeanConverter extends ConfigurableMapper implements ApplicationContextAware {

  private MapperFactory factory;

  private ApplicationContext applicationContext;

  @Autowired(required = false)
  private List<Converter> converters;

  public OrikaBeanConverter() {
    super(false);
  }

  @Override
  protected void configure(MapperFactory factory) {
    this.factory = factory;
    addAllSpringBeans(applicationContext);
    setConverters();
  }

  @Override
  protected void configureFactoryBuilder(final DefaultMapperFactory.Builder factoryBuilder) {
  }

  @PostConstruct
  public void init() {
    super.init();
  }

  public void addConverter(Converter<?, ?> converter) {
    factory.getConverterFactory().registerConverter(converter);
  }


  private void addAllSpringBeans(final ApplicationContext applicationContext) {
    if (applicationContext != null) {
      Map<String, Converter> converters = applicationContext.getBeansOfType(Converter.class);

      for (Converter converter : converters.values()) {
        addConverter(converter);
      }
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
    init();
  }

  private void setConverters() {
    if (converters != null) {
      for (Converter converter : converters) {
        addConverter(converter);
      }
    }
  }
}
