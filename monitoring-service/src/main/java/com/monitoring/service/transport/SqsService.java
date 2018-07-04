package com.monitoring.service.transport;

import com.monitoring.receiver.payload.VehicleStatusPayload;
import com.monitoring.service.event.VehicleStatusEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Sqs message service for processing sqs messages
 */
@Slf4j
@Component
class SqsService {

  private MessageReceiver receiver;

  private ApplicationEventPublisher eventPublisher;

  @Autowired
  public SqsService(MessageReceiver receiver, ApplicationEventPublisher eventPublisher) {
    this.receiver = receiver;
    this.eventPublisher = eventPublisher;
  }

  /**
   * Retrieve and process Vehicle status payloads
   */
  @Scheduled(fixedDelayString = "${queue.schedule}")
  public void requestMessages() {
    List<VehicleStatusPayload> messages = receiver.getMessages();
    if (messages.size() > 0) {
      eventPublisher.publishEvent(new VehicleStatusEvent(messages));
    }
  }
}
