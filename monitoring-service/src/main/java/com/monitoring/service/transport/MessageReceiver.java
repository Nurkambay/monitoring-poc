package com.monitoring.service.transport;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageBatchRequest;
import com.amazonaws.services.sqs.model.DeleteMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.monitoring.receiver.payload.VehicleStatusPayload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

/**
 * Retrieves messages from sqs queue
 */
@Slf4j
@Component
public class MessageReceiver {

  @Value("${queue.url}")
  private String queueUrl;

  @Value("${queue.max.messages}")
  private int maxMessages;

  private AmazonSQS sqs;
  private ObjectMapper mapper;

  @Autowired
  public MessageReceiver(AmazonSQS sqs) {
    this.sqs = sqs;

    this.mapper = new ObjectMapper();
    this.mapper.setDateFormat(new ISO8601DateFormat());
    this.mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  /**
   * Request messages from sqs queue
   * @return list of Vehicle status payload messages
   */
  public List<VehicleStatusPayload> getMessages() {
    log.debug("[MessageReceiver] request messages");
    final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
    receiveMessageRequest.setMaxNumberOfMessages(maxMessages);

    final List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
    List<VehicleStatusPayload> result = processMessages(messages);

    log.debug("[MessageReceiver] finish processing");
    return result;
  }

  /**
   * Process messages. Parse sqs messages to Vehicle status payloads
   * @param messages
   * @return
   */
  private List<VehicleStatusPayload> processMessages(List<Message> messages) {
    List<VehicleStatusPayload> result = new LinkedList<>();
    if (messages.size() > 0) {
      log.debug("[MessageReceiver] messages received: {}", messages.size());
      for (Message message : messages) {
        try {
          result.add(mapper.readValue(message.getBody(), VehicleStatusPayload.class));
        } catch (IOException e) {
          log.warn("[MessageReceiver] cannot parse message: {}, skip.", message.getBody());
        }
      }
      log.debug("[MessageReceiver] delete messages from queue");
      deleteMessages(messages);
    }

    return result;
  }

  /**
   * Delete messages from sqs queue
   * @param messages list of messages to delete
   */
  private void deleteMessages(List<Message> messages) {
    DeleteMessageBatchRequest request = new DeleteMessageBatchRequest(queueUrl);

    List<DeleteMessageBatchRequestEntry> entries = new LinkedList<>();
    for (Message message : messages) {
      entries.add(new DeleteMessageBatchRequestEntry(UUID.randomUUID().toString(), message.getReceiptHandle()));
    }

    request.setEntries(entries);
    sqs.deleteMessageBatch(request);
  }
}
