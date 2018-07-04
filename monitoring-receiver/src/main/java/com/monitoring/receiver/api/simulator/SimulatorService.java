package com.monitoring.receiver.api.simulator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monitoring.receiver.api.transport.MessageSender;
import com.monitoring.receiver.payload.VehicleStatusPayload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

/**
 * Simulator Service randomly simulates Vehicle activity
 */
@Slf4j
@ConditionalOnProperty(prefix = "simulator", name = "enabled")
@Component
public class SimulatorService {

  private static String[]
      regNumbers = {"ABC123", "DEF456", "GHI789", "JKL012", "MNO345", "PQR678", "STU901"};

  private MessageSender messageSender;

  @Autowired
  public SimulatorService(MessageSender messageSender) {
    this.messageSender = messageSender;
  }

  /**
   * Simulates Vehicle activity by sending messages to event bus
   */
  @Scheduled(fixedDelayString = "${simulator.schedule}")
  public void runSimulation() {
    Random r = new Random();

    for (String regNum : regNumbers) {
      if (r.nextInt(3) == 1) {
        try {
          messageSender.sendMessage(VehicleStatusPayload
                                        .builder()
                                        .regno(regNum)
                                        .timestamp(new Timestamp(System.currentTimeMillis()))
                                        .build());
        } catch (JsonProcessingException e) {
          log.warn("[SimulatorService] cannot send simulator message: {}", e);
        }
      }
    }
  }
}
