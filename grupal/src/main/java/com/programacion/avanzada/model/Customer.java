package com.programacion.avanzada.model;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;
import jakarta.validation.constraints.Email;
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
public class Customer {
  @Id private String id;

  @NotBlank(message = "customer name cannot be empty")
  @Column
  private String name;

  @Email(message = "email format not valid")
  @NotBlank
  @Column
  private String email;

  @Column private Integer version;
}
