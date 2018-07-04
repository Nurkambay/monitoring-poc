package com.monitoring.model.entities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for Vehicle BD entity
 */
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
  VehicleEntity findByRegno(String regno);
  List<VehicleEntity> findByCustomersId(Long customerId);
}
