package com.monitoring.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monitoring.integration.transport.MessageReceiver;
import com.monitoring.integration.transport.MessageSender;
import com.monitoring.model.entities.VehicleEntity;
import com.monitoring.model.entities.VehicleRepository;
import com.monitoring.receiver.payload.VehicleStatusPayload;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { IntegrationApplicationConfig.class})
public class IntegrationTest {

  @Value("${queue.schedule}")
  private long queueSchedule;

  @Autowired
  private MessageSender messageSender;

  @Autowired
  private MessageReceiver messageReceiver;

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @Autowired
  private VehicleRepository vehicleRepository;

  private static final String TEST_REGNO = "test-regno";
  private static final String TEST_VIN = "test-vin";
  public static CountDownLatch countDownLatch;

  public static int catchedMessages;

  @Test
  public void testInfrastructure() throws InterruptedException, JsonProcessingException {
    messageSender.sendMessage(VehicleStatusPayload.builder()
                                  .regno(TEST_REGNO)
                                  .timestamp(new Timestamp(System.currentTimeMillis()))
                                  .build());

    countDownLatch = new CountDownLatch(1);
    countDownLatch.await(queueSchedule * 2, TimeUnit.MILLISECONDS);

    assertThat(catchedMessages, equalTo(1));
  }

  private void cleanup() {
    VehicleEntity vehicleEntity = vehicleRepository.findByRegno(TEST_REGNO);
    if (vehicleEntity != null) {
      vehicleRepository.delete(vehicleEntity);
    }
  }

  @Before
  public void setup() {
    cleanup();
    vehicleRepository.save(VehicleEntity.builder()
                               .regno(TEST_REGNO)
                               .vin(TEST_VIN)
                               .build());
  }

  @After
  public void teardown() {
    cleanup();
  }

  public static class TestApplicationEvent extends ApplicationEvent {
    public TestApplicationEvent(List<VehicleStatusPayload> source) {
      super(source);
    }

    @Override
    public List<VehicleStatusPayload> getSource() {
      return (List<VehicleStatusPayload>) super.getSource();
    }
  }

  @Scheduled(fixedDelay = 1000)
  public void receiveMessages() {
    eventPublisher.publishEvent(new TestApplicationEvent(messageReceiver.getMessages()));
  }

  @Component
  public static class EventHandler {
    @Autowired
    private VehicleRepository vehicleRepository;

    @EventListener
    public void eventHandler(TestApplicationEvent event) {
      for (VehicleStatusPayload statusPayload : event.getSource()) {
        VehicleEntity vehicleEntity = vehicleRepository.findByRegno(statusPayload.getRegno());
        if (vehicleEntity != null && statusPayload.getTimestamp() != null) {
          vehicleEntity.setStatusUpdatedAt(statusPayload.getTimestamp());
          vehicleRepository.save(vehicleEntity);

          catchedMessages++;
          countDownLatch.countDown();
        }
      }
    }
  }

}
