package com.monitoring.site.api.service;

import com.monitoring.model.entities.VehicleEntity;
import com.monitoring.model.entities.VehicleRepository;
import com.monitoring.site.api.converter.OrikaBeanConverter;
import com.monitoring.site.api.filter.VehicleFilter;
import com.monitoring.site.api.model.Vehicle;
import com.monitoring.site.api.filter.VehicleFilterForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Vehicle service
 */
@Slf4j
@Component
@Transactional
public class VehicleService {

  private VehicleRepository vehicleRepository;
  private OrikaBeanConverter mapper;

  @Autowired
  public VehicleService(VehicleRepository vehicleRepository,
                        OrikaBeanConverter mapper) {
    this.vehicleRepository = vehicleRepository;
    this.mapper = mapper;
  }

  /**
   * List Vehicles with filtering, sorted by reg. number
   * @param filterForm filter form
   * @return list of Vehicles
   */
  public List<Vehicle> list(VehicleFilterForm filterForm) {
    List<VehicleEntity> vehicles = null;
    long customerId = Optional.ofNullable(filterForm.getOwner()).orElse(-1L);
    if (customerId >=0) {
      vehicles = vehicleRepository.findByCustomersId(customerId);
    }

    if (vehicles == null) {
      vehicles = vehicleRepository.findAll();
    }

    return vehicles.stream()
        .map(s -> mapper.map(s, Vehicle.class))
        .filter(v -> VehicleFilter.apply(v, filterForm.getStatus()))
        .sorted(Comparator.comparing(Vehicle::getRegno))
        .collect(Collectors.toList());
  }
}
