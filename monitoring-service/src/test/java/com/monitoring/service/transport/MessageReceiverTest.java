package com.monitoring.service.transport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoring.receiver.payload.VehicleStatusPayload;
import com.monitoring.service.TestServiceApplicationConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestServiceApplicationConfig.class)
public class MessageReceiverTest {

  private static final int MESSAGE_COUNT = 4;

  @Mock
  private AmazonSQS sqs;

  private MessageReceiver messageReceiver;

  @Test
  public void testGetMessages() {
    assertThat(messageReceiver.getMessages().size(), equalTo(MESSAGE_COUNT));
  }

  @Before
  public void setup() throws JsonProcessingException {
    messageReceiver = new MessageReceiver(sqs);

    ObjectMapper mapper = new ObjectMapper();
    List<Message> messages = new LinkedList<>();
    for (int i = 0; i < MESSAGE_COUNT; i++) {
      Message message = new Message();
      message.setBody(mapper.writeValueAsString(VehicleStatusPayload.builder().build()));
      messages.add(message);
    }

    ReceiveMessageResult messageResult = new ReceiveMessageResult();
    messageResult.setMessages(messages);

    Mockito
        .when(sqs.receiveMessage(Mockito.any(ReceiveMessageRequest.class)))
        .thenReturn(messageResult);
  }
}
