package com.monitoring.site.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base data model
 */
@Data
@NoArgsConstructor
public abstract class BaseModel {
  private Long id;
}
