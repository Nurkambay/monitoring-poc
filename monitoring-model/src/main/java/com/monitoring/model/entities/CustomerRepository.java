package com.monitoring.model.entities;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Customer BD entity
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}
