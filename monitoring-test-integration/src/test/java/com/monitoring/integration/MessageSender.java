package com.monitoring.integration;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.monitoring.receiver.payload.VehicleStatusPayload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Service for sending messages to Amazon sqs queue
 */
@Component
public class MessageSender {

  @Value("${queue.url}")
  private String queueUrl;

  private AmazonSQS sqs;
  private ObjectMapper mapper;

  @Autowired
  public MessageSender(AmazonSQS sqs) {
    this.sqs = sqs;

    this.mapper = new ObjectMapper();
    this.mapper.setDateFormat(new ISO8601DateFormat());
    this.mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  /**
   * Send message payload to Amazon sqs queue
   * @param payload to send
   */
  @Async
  public void sendMessage(VehicleStatusPayload payload) throws JsonProcessingException {
    SendMessageRequest send_msg_request = new SendMessageRequest()
        .withQueueUrl(queueUrl)
        .withMessageBody(mapper.writeValueAsString(payload));

    sqs.sendMessage(send_msg_request);

  }
}
