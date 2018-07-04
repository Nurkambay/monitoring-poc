package com.monitoring.site.api.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.monitoring.site.api.model.Vehicle;

import org.junit.Test;

public class VehicleFilterTest {

  @Test
  public void applyTest() {
    boolean result = VehicleFilter.apply(Vehicle.builder().online(true).build(), VehicleFilterStatus.ANY);
    assertThat(result, equalTo(true));

    result = VehicleFilter.apply(Vehicle.builder().online(false).build(), VehicleFilterStatus.ANY);
    assertThat(result, equalTo(true));

    result = VehicleFilter.apply(Vehicle.builder().online(true).build(), VehicleFilterStatus.ONLINE);
    assertThat(result, equalTo(true));

    result = VehicleFilter.apply(Vehicle.builder().online(false).build(), VehicleFilterStatus.ONLINE);
    assertThat(result, equalTo(false));

    result = VehicleFilter.apply(Vehicle.builder().online(true).build(), VehicleFilterStatus.OFFLINE);
    assertThat(result, equalTo(false));

    result = VehicleFilter.apply(Vehicle.builder().online(false).build(), VehicleFilterStatus.OFFLINE);
    assertThat(result, equalTo(true));
  }
}
