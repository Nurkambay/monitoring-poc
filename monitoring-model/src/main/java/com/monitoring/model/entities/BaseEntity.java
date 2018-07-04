package com.monitoring.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Base DB entity with id and created_at, updated_at fields
 */
@NoArgsConstructor
@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  protected Long id;

  @Basic
  @Column(name = "created_at", nullable = false)
  protected Timestamp createdAt = new Timestamp(System.currentTimeMillis());

  @Basic
  @Column(name = "updated_at", nullable = false)
  protected Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
}
