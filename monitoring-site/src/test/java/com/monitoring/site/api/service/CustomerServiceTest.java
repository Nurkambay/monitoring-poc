package com.monitoring.site.api.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.monitoring.model.entities.CustomerEntity;
import com.monitoring.model.entities.CustomerRepository;
import com.monitoring.site.api.TestSiteApplicationConfig;
import com.monitoring.site.api.converter.OrikaBeanConverter;
import com.monitoring.site.api.model.Customer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestSiteApplicationConfig.class)
public class CustomerServiceTest {

  private static final int CUSTOMER_COUNT = 7;

  @Mock
  private CustomerRepository customerRepository;

  @Autowired
  private OrikaBeanConverter converter;

  private CustomerService customerService;

  private List<CustomerEntity> entities;

  @Test
  public void testList() {
    List<Customer> result = customerService.list();
    assertThat(result.size(), equalTo(CUSTOMER_COUNT));

    List<String> resultNames = result.stream().map(Customer::getName).collect(Collectors.toList());
    List<String> originalNames =
        entities.stream().map(CustomerEntity::getName)
            .sorted(Comparator.comparing(String::toString)).collect(Collectors.toList());

    assertThat(resultNames, equalTo(originalNames));
  }

  @Before
  public void setup() {
    entities = new LinkedList<>();
    for (int i = 0; i < CUSTOMER_COUNT; i++) {
      entities.add(CustomerEntity.builder()
                       .name(UUID.randomUUID().toString())
                       .address(UUID.randomUUID().toString())
                       .build());
    }

    customerService = new CustomerService(customerRepository, converter);
    Mockito.when(customerRepository.findAll()).thenReturn(entities);
  }
}
