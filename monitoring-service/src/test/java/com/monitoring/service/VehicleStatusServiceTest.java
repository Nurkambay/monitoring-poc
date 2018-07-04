package com.monitoring.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.monitoring.model.entities.VehicleEntity;
import com.monitoring.model.entities.VehicleRepository;
import com.monitoring.receiver.payload.VehicleStatusPayload;
import com.monitoring.service.event.VehicleStatusEvent;

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
public class VehicleStatusServiceTest {

  @Mock
  private VehicleRepository vehicleRepository;

  private VehicleStatusService vehicleStatusService;
  private static String existingRegno = "ABC123";

  @Test
  public void testProcessEvent() {
    List<VehicleStatusPayload> payloads  = new LinkedList<>();
    payloads.add(VehicleStatusPayload.builder().build());
    payloads.add(VehicleStatusPayload.builder().build());
    payloads.add(VehicleStatusPayload.builder().regno(existingRegno).build());

    int processed = vehicleStatusService.processEvent(new VehicleStatusEvent(payloads));
    assertThat(processed, equalTo(1));
  }

  @Before
  public void setup() {
    vehicleStatusService = new VehicleStatusService(vehicleRepository);

    Mockito.when(vehicleRepository.findByRegno(existingRegno)).thenAnswer(
      invocationOnMock -> new VehicleEntity()
    );
  }

}
