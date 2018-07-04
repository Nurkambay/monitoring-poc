package com.monitoring.site.api.converter;

import com.monitoring.site.api.model.Customer;
import com.monitoring.model.entities.CustomerEntity;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.metadata.Type;

import org.springframework.stereotype.Component;

/**
 * Customer converter. Converts Customer DB entity to Customer data model
 */
@Component
public class CustomerConverter extends CustomConverter<CustomerEntity, Customer> {

  @Override
  public Customer convert(CustomerEntity source, Type<? extends Customer> destinationType) {
    Customer result = new Customer();
    ConverterHelper.apply(source, result);

    result.setId(source.getId());
    result.setName(source.getName());
    result.setAddress(source.getAddress());
    return result;
  }
}
