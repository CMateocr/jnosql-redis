package com.programacion.avanzada.model;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder {
  @Id private String id;

  @NotNull @Column private String customerId;

  @Column private LocalDateTime placedOn;

  @Column private LocalDateTime deliveredOn;

  @Column private Status status;

  @Column private Double total;

  @NotEmpty(message = "order must have at least one item")
  @Valid
  @Column
  private List<LineItem> items;
}
