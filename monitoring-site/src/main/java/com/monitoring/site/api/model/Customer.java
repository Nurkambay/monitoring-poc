package com.monitoring.site.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Customer data model
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Customer extends BaseModel {
  private String name;
  private String address;
}
