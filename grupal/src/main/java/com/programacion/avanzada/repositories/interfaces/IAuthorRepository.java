package com.programacion.avanzada.repositories.interfaces;

import com.programacion.avanzada.model.Author;
import java.util.Iterator;
import java.util.Set;

public interface IAuthorRepository extends IRepository<Author, String> {
  Iterator<Author> findAllByIds(Set<String> ids);
}
