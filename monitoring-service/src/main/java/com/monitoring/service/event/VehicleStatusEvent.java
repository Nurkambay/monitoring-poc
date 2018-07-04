package com.monitoring.service.event;

import com.monitoring.receiver.payload.VehicleStatusPayload;

import org.springframework.context.ApplicationEvent;

import java.util.List;

import lombok.Data;

/**
 * Vehicle status internal event
 */
@Data
public class VehicleStatusEvent extends ApplicationEvent {

  public VehicleStatusEvent(List<VehicleStatusPayload> payloadList) {
    super(payloadList);
  }

  @Override
  public List<VehicleStatusPayload> getSource() {
    return (List<VehicleStatusPayload>) super.getSource();
  }


}
