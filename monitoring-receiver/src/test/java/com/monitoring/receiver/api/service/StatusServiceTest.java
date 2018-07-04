package com.monitoring.receiver.api.service;

import com.monitoring.receiver.api.TestReceiverApplicationConfig;
import com.monitoring.receiver.api.model.VehicleStatusForm;
import com.monitoring.receiver.api.transport.MessageSender;
import com.monitoring.receiver.payload.VehicleStatusPayload;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestReceiverApplicationConfig.class)
public class StatusServiceTest {

  @Mock
  private MessageSender messageSender;

  private StatusService statusService;

  @Test(expected=NullPointerException.class)
  public void testUpdateStatusNull() throws ServiceException {
    statusService.updateStatus(null);
  }

  @Test
  public void testUpdateStatus() throws ServiceException {
    VehicleStatusForm form = new VehicleStatusForm();
    statusService.updateStatus(form);
  }

  @Before
  public void setup() {
    statusService = new StatusService(messageSender);
  }

}
