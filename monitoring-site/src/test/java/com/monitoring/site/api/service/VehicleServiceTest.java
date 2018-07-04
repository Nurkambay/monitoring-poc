package com.monitoring.site.api.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.monitoring.model.entities.VehicleEntity;
import com.monitoring.model.entities.VehicleRepository;
import com.monitoring.site.api.TestSiteApplicationConfig;
import com.monitoring.site.api.converter.OrikaBeanConverter;
import com.monitoring.site.api.model.VehicleHelper;
import com.monitoring.site.api.filter.VehicleFilterForm;
import com.monitoring.site.api.filter.VehicleFilterStatus;
import com.monitoring.site.api.model.Vehicle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestSiteApplicationConfig.class)
public class VehicleServiceTest {

  private static final long CUSTOMER_ID = 10;

  @Value("${vehicle.timeout}")
  private long vehicleTimeout;

  @Mock
  private VehicleRepository vehicleRepository;

  @Autowired
  private OrikaBeanConverter converter;

  @Autowired
  private VehicleHelper vehicleHelper;

  private VehicleService vehicleService;
  private List<VehicleEntity> allVehicles;
  private List<VehicleEntity> customerVehicles;

  @Test
  public void testListEmptyFilter1() {
    check(getEmptyFilter1(), allVehicles);
  }

  @Test
  public void testListEmptyFilter2() {
    check(getEmptyFilter2(), allVehicles);
  }

  @Test
  public void testOnlineAnyFilter() {
    VehicleFilterForm vehicleFilterForm = getEmptyFilter1();
    vehicleFilterForm.setStatus(VehicleFilterStatus.ONLINE);

    check(vehicleFilterForm, allVehicles.stream()
        .filter(v -> vehicleHelper.isOnline(v))
        .collect(Collectors.toList()));
  }

  @Test
  public void testOfflineAnyFilter() {
    VehicleFilterForm vehicleFilterForm = getEmptyFilter1();
    vehicleFilterForm.setStatus(VehicleFilterStatus.OFFLINE);

    check(vehicleFilterForm, allVehicles.stream()
        .filter(v -> !vehicleHelper.isOnline(v))
        .collect(Collectors.toList()));
  }

  @Test
  public void testAnyCustomerFilter() {
    VehicleFilterForm vehicleFilterForm = getAnyCustomerFilter();
    check(vehicleFilterForm, customerVehicles);
  }

  @Test
  public void testOnlineCustomerFilter() {
    VehicleFilterForm vehicleFilterForm = getAnyCustomerFilter();
    vehicleFilterForm.setStatus(VehicleFilterStatus.ONLINE);

    check(vehicleFilterForm, customerVehicles.stream()
        .filter(v -> vehicleHelper.isOnline(v))
        .collect(Collectors.toList()));
  }

  @Test
  public void testOfflineCustomerFilter() {
    VehicleFilterForm vehicleFilterForm = getAnyCustomerFilter();
    vehicleFilterForm.setStatus(VehicleFilterStatus.OFFLINE);

    check(vehicleFilterForm, customerVehicles.stream()
        .filter(v -> !vehicleHelper.isOnline(v))
        .collect(Collectors.toList()));
  }

  private void check(VehicleFilterForm vehicleFilterForm, List<VehicleEntity> vehicleEntities) {
    List<Vehicle> vehicles = vehicleService.list(vehicleFilterForm);

    List<String> resultRegnums = vehicles.stream().map(Vehicle::getRegno).collect(Collectors.toList());
    List<String> originalRegnums = vehicleEntities.stream()
        .map(VehicleEntity::getRegno)
        .sorted(Comparator.comparing(String::toString))
        .collect(Collectors.toList());

    assertThat(resultRegnums, equalTo(originalRegnums));
  }

  private VehicleFilterForm getEmptyFilter1() {
    return VehicleFilterForm.builder()
        .status(VehicleFilterStatus.ANY)
        .build();
  }

  private VehicleFilterForm getEmptyFilter2() {
    return VehicleFilterForm.builder()
        .status((VehicleFilterStatus.ANY)).owner(-1L)
        .build();
  }

  private VehicleFilterForm getAnyCustomerFilter() {
    return VehicleFilterForm.builder()
        .status((VehicleFilterStatus.ANY)).owner(CUSTOMER_ID)
        .build();
  }

  @Before
  public void setup() {
    vehicleService = new VehicleService(vehicleRepository, converter);

    allVehicles = new LinkedList<>();
    allVehicles.add(getVehicleEntity(true));
    allVehicles.add(getVehicleEntity(false));
    Mockito.when(vehicleRepository.findAll()).thenReturn(allVehicles);

    customerVehicles = new LinkedList<>();
    customerVehicles.add(getVehicleEntity(true));
    customerVehicles.add(getVehicleEntity(true));
    customerVehicles.add(getVehicleEntity(false));
    customerVehicles.add(getVehicleEntity(false));
    Mockito.when(vehicleRepository.findByCustomersId(CUSTOMER_ID)).thenReturn(customerVehicles);
  }

  private VehicleEntity getVehicleEntity(boolean online) {
    return VehicleEntity.builder()
        .regno(UUID.randomUUID().toString())
        .customers(new LinkedList<>())
        .vin(UUID.randomUUID().toString())
        .statusUpdatedAt(online ? new Timestamp(System.currentTimeMillis()) :
                         new Timestamp((System.currentTimeMillis() - (vehicleTimeout + 1) * 1000)))
        .build();
  }
}
