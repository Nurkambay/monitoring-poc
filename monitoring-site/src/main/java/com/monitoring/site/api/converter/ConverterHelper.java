package com.monitoring.site.api.converter;

import com.monitoring.site.api.model.BaseModel;
import com.monitoring.model.entities.BaseEntity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Converter Helper
 */
public abstract class ConverterHelper {
  public static void apply(BaseEntity source, BaseModel destination) {
    destination.setId(source.getId());
  }

  public static void apply(BaseModel source, BaseEntity destination) {
    destination.setId(source.getId());
    if(source.getId() == null)
    {
      destination.setCreatedAt(new Timestamp(new Date().getTime()));
    }
    destination.setUpdatedAt(new Timestamp(new Date().getTime()));
  }
}