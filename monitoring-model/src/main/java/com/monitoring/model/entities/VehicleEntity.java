package com.monitoring.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.sql.Timestamp;
import java.util.List;

/**
 * Vehicle DB entity
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle")
public class VehicleEntity extends BaseEntity {

  @Column(name = "vin", nullable = false)
  private String vin;

  @Column(name = "regno", nullable = false)
  private String regno;

  @Column(name = "status_updated_at")
  protected Timestamp statusUpdatedAt;

  @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
  @JoinTable(
      name = "customer_vehicle",
      joinColumns = {@JoinColumn(name = "vehicle_id")},
      inverseJoinColumns = {@JoinColumn(name = "customer_id")}
  )
  private List<CustomerEntity> customers;
}
