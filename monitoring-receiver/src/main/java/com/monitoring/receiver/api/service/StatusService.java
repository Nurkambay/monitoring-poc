package com.monitoring.receiver.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monitoring.receiver.api.model.VehicleStatusForm;
import com.monitoring.receiver.api.transport.MessageSender;
import com.monitoring.receiver.payload.VehicleStatusPayload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for Status Controller
 */
@Slf4j
@Service
public class StatusService {

  private MessageSender messageSender;

  @Autowired
  public StatusService(MessageSender messageSender) {
    this.messageSender = messageSender;
  }

  /**
   * Updates status by sending message to event bus
   * @param vehicleStatusForm with vehicle information
   */
  public void updateStatus(VehicleStatusForm vehicleStatusForm) throws ServiceException {

    try {
      messageSender.sendMessage(VehicleStatusPayload
                                    .builder()
                                    .regno(vehicleStatusForm.getRegno())
                                    .timestamp(new Timestamp(System.currentTimeMillis()))
                                    .build());
    } catch (JsonProcessingException e) {
      log.warn("[StatusService] cannot send message: {}", e);
      throw new ServiceException(e.getMessage());
    }
  }
}
