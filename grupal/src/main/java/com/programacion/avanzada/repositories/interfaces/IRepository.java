package com.programacion.avanzada.repositories.interfaces;

import java.util.Optional;

public interface IRepository<T, U> {
  T save(T entity);

  void deleteById(U id);

  Optional<T> findById(U id);
}
