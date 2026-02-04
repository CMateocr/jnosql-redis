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
public class Author {
  @Id private String id;

  @NotBlank(message = "author name must not be empty")
  @Column
  private String name;

  @Column private Integer version;
}
