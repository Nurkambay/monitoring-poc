package com.monitoring.receiver.api.transport;

import com.amazonaws.services.sqs.AmazonSQS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.monitoring.receiver.api.TestReceiverApplicationConfig;
import com.monitoring.receiver.payload.VehicleStatusPayload;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestReceiverApplicationConfig.class)
public class MessageSenderTest {

  @Mock
  private AmazonSQS sqs;

  private MessageSender messageSender;

  @Test
  public void testSendMessage() throws JsonProcessingException {
    messageSender.sendMessage(VehicleStatusPayload
                                  .builder()
                                  .build());
  }

  @Before
  public void setup() {
    messageSender = new MessageSender(sqs);
  }

}
