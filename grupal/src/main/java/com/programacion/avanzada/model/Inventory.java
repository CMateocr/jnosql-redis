package com.programacion.avanzada.model;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
  @Id @NotBlank private String id;

  @Column @NotBlank private String bookIsbn;

  @Column private Integer sold;

  @Column private Integer supplied;

  @Column private Integer version;
}
