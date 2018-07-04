package com.monitoring.receiver.api.model;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Vehicle status", description = "Set vehicle status to \"online\"")
public class VehicleStatusForm {
  @ApiModelProperty(value = "Vehicle registration number", dataType = "java.lang.String", example = "ABC123")
  @NotNull
  private String regno;
}