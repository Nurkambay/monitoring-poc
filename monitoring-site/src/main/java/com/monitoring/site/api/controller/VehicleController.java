package com.monitoring.site.api.controller;

import com.monitoring.site.api.model.Vehicle;
import com.monitoring.site.api.filter.VehicleFilterForm;
import com.monitoring.site.api.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleController {

  private VehicleService vehicleService;

  @Autowired
  public VehicleController(VehicleService vehicleService) {
    this.vehicleService = vehicleService;
  }

  @RequestMapping(value = "/filter", method = RequestMethod.POST)
  public List<Vehicle> listVehicles(@RequestBody VehicleFilterForm filter) {
    return vehicleService.list(filter);
  }

}
