package com.monitoring.site.api.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.monitoring.model.entities.VehicleEntity;
import com.monitoring.site.api.TestSiteApplicationConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestSiteApplicationConfig.class)
public class VehicleHelperTest {

  @Value("${vehicle.timeout}")
  private long vehicleTimeout;

  @Autowired
  private VehicleHelper vehicleHelper;

  @Test
  public void testIsOnline() {
    boolean result = vehicleHelper.isOnline(VehicleEntity.builder().build());
    assertThat(result, equalTo(false));

    result = vehicleHelper
        .isOnline(VehicleEntity
                      .builder()
                      .statusUpdatedAt(new Timestamp(System.currentTimeMillis() - vehicleTimeout * 1000 + 10))
                      .build());

    assertThat(result, equalTo(true));

    result = vehicleHelper
        .isOnline(VehicleEntity
                      .builder()
                      .statusUpdatedAt(new Timestamp(System.currentTimeMillis() - vehicleTimeout * 1000 - 10))
                      .build());

    assertThat(result, equalTo(false));

  }
}
