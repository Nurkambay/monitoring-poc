package com.monitoring.site.api.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Vehicle Filter Form
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VehicleFilterForm {
  private VehicleFilterStatus status;
  private Long owner;
}
