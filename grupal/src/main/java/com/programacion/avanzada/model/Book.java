package com.programacion.avanzada.model;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class Book {
  @Id
  @NotBlank(message = "ISBN cannot be blank")
  private String isbn;

  @Column
  @NotBlank(message = "book title must not be blank")
  private String title;

  @Positive(message = "price must be positive")
  @Column
  private Double price;

  @Column private Integer version;

  @NotNull @Column private List<String> authorIds;
}
