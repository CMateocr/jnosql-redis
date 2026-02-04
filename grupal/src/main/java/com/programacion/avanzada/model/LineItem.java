package com.programacion.avanzada.model;

import jakarta.nosql.Column;
import jakarta.nosql.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable(Embeddable.EmbeddableType.GROUPING)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LineItem {
  @Column private Integer idx;

  @Column private Integer quantity;

  @Column private String bookIsbn;
}
