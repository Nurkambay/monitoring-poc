package com.monitoring.site.api.service;

import com.monitoring.model.entities.CustomerRepository;
import com.monitoring.site.api.converter.OrikaBeanConverter;
import com.monitoring.site.api.model.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Customer service
 */
@Slf4j
@Component
@Transactional
public class CustomerService {

  private final OrikaBeanConverter mapper;
  private CustomerRepository customerRepository;

  @Autowired
  public CustomerService(CustomerRepository customerRepository,
                         OrikaBeanConverter mapper) {
    this.customerRepository = customerRepository;
    this.mapper = mapper;
  }

  /**
   * List all Customers, sorted by name
   * @return list of Customers
   */
  public List<Customer> list() {
    return customerRepository.findAll().stream()
        .map(c -> mapper.map(c, Customer.class))
        .sorted(Comparator.comparing(Customer::getName))
        .collect(Collectors.toList());
  }
}
