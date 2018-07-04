package com.monitoring.site.api.filter;

import com.monitoring.site.api.model.Vehicle;

/**
 * Vehicle Filter helper class
 */
public abstract class VehicleFilter {
  public static boolean apply(Vehicle vehicle, VehicleFilterStatus status) {
    switch (status) {
      case OFFLINE:
        return !vehicle.isOnline();
      case ONLINE:
        return vehicle.isOnline();
      default:
        return true;
    }
  }
}
