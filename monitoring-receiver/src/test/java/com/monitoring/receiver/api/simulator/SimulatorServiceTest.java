package com.monitoring.receiver.api.simulator;

import com.monitoring.receiver.api.TestReceiverApplicationConfig;
import com.monitoring.receiver.api.transport.MessageSender;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestReceiverApplicationConfig.class)
public class SimulatorServiceTest {

  @Mock
  private MessageSender messageSender;

  private SimulatorService simulatorService;

  @Test
  public void testSimulation() {
    simulatorService.runSimulation();
  }

  @Before
  public void setup() {
    simulatorService = new SimulatorService(messageSender);
  }

}
