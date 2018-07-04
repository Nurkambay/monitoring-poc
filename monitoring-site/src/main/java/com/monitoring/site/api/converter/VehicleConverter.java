package com.monitoring.site.api.converter;

import com.monitoring.model.entities.CustomerEntity;
import com.monitoring.site.api.model.Vehicle;
import com.monitoring.model.entities.VehicleEntity;
import com.monitoring.site.api.model.VehicleHelper;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.metadata.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Vehicle converter. Converts Vehicle DB entity to Vehicle data model
 */
@Component
public class VehicleConverter extends CustomConverter<VehicleEntity, Vehicle> {

  private VehicleHelper vehicleHelper;

  @Autowired
  public VehicleConverter(VehicleHelper vehicleHelper) {
    this.vehicleHelper = vehicleHelper;
  }

  @Override
  public Vehicle convert(VehicleEntity source, Type<? extends Vehicle> destinationType) {
    Vehicle result = new Vehicle();
    ConverterHelper.apply(source, result);
    result.setVin(source.getVin());
    result.setRegno(source.getRegno());
    result.setOnline(vehicleHelper.isOnline(source));

    List<String> owners = source.getCustomers().stream()
        .map(CustomerEntity::getName)
        .sorted()
        .collect(Collectors.toList());

    result.setOwners(String.join(",", owners));
    return result;
  }

}
