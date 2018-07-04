package com.monitoring.service.transport;

import com.monitoring.receiver.payload.VehicleStatusPayload;
import com.monitoring.service.TestServiceApplicationConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestServiceApplicationConfig.class)
public class SqsServiceTest {

  private SqsService sqsService;

  private CountDownLatch latch = new CountDownLatch(1);

  @Mock
  private MessageReceiver messageReceiver;

  @Mock
  private ApplicationEventPublisher eventPublisher;

  @Test
  public void testRetrieveMessages() throws InterruptedException {
    sqsService.requestMessages();
    latch.await(1, TimeUnit.SECONDS);
  }

  @Before
  public void setup() {
    sqsService = new SqsService(messageReceiver, eventPublisher);

    List<VehicleStatusPayload> messages = new LinkedList<>();
    messages.add(VehicleStatusPayload.builder().build());

    Mockito.when(messageReceiver.getMessages()).thenReturn(messages);
    Mockito.doAnswer(a -> {
      latch.countDown();
      return true;
    }).when(eventPublisher).publishEvent(Mockito.any());
  }

}
