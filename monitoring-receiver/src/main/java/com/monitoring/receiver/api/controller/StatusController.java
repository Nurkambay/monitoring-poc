package com.monitoring.receiver.api.controller;

import com.monitoring.receiver.api.model.VehicleStatusForm;
import com.monitoring.receiver.api.service.ServiceException;
import com.monitoring.receiver.api.service.StatusService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/status", produces = "text/html")
public class StatusController {

  private StatusService statusService;

  @Autowired
  public StatusController(StatusService statusService) {
    this.statusService = statusService;
  }

  @RequestMapping(value = "/health", method = RequestMethod.GET)
  public String healthCheck() {
    return "Ok";
  }

  @ApiOperation(value = "Post online status for vehicle ")
  @RequestMapping(value = "online/", method = RequestMethod.POST)
  public void updateStatus(@RequestBody VehicleStatusForm vehicleStatusForm)
      throws ServiceException {
    statusService.updateStatus(vehicleStatusForm);
  }
}
