package com.monitoring.site.api.model;

import com.monitoring.model.entities.VehicleEntity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VehicleHelper {
  @Value("${vehicle.timeout}")
  private long vehicleTimeout;

  public boolean isOnline(VehicleEntity vehicleEntity) {
    boolean online = false;
    if (vehicleEntity.getStatusUpdatedAt() != null) {
      long seconds = System.currentTimeMillis() - vehicleEntity.getStatusUpdatedAt().getTime();
      online = (seconds <= vehicleTimeout * 1000);
    }
    return online;
  }
}
