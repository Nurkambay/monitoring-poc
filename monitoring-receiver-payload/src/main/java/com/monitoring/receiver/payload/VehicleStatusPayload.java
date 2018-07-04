package com.monitoring.receiver.payload;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Vehicle status message payload
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleStatusPayload {
  private String regno;
  private Timestamp timestamp;
}
