package com.monitoring.service;

import com.monitoring.receiver.payload.VehicleStatusPayload;
import com.monitoring.model.entities.VehicleEntity;
import com.monitoring.model.entities.VehicleRepository;
import com.monitoring.service.event.VehicleStatusEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * Vehicle status service catches internal event and update Vehicle status
 */
@Slf4j
@Component
@Transactional
public class VehicleStatusService {

  private VehicleRepository vehicleRepository;

  @Autowired
  public VehicleStatusService(VehicleRepository vehicleRepository) {
    this.vehicleRepository = vehicleRepository;
  }

  /**
   * Process Vehicle status payload event
   * @param event message with Vehicle status payloads
   */
  @EventListener
  public int processEvent(VehicleStatusEvent event) {
    log.info("[VehicleService] start processing status messages");

    int count = 0;
    for (VehicleStatusPayload statusPayload : event.getSource()) {
      VehicleEntity vehicleEntity = vehicleRepository.findByRegno(statusPayload.getRegno());
      if (vehicleEntity != null) {
        count++;
        vehicleEntity.setStatusUpdatedAt(statusPayload.getTimestamp());
        vehicleRepository.save(vehicleEntity);
      } else {
        log.warn("[VehicleService] cannot find vehicle with reg. number {}", statusPayload.getRegno());
      }
    }

    log.info("[VehicleService] finish processing status messages: {}", count);
    return count;
  }
}
