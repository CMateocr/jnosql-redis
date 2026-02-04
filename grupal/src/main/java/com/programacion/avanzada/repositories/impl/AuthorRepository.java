package com.programacion.avanzada.repositories.impl;

import com.programacion.avanzada.model.Author;
import com.programacion.avanzada.repositories.interfaces.IAuthorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.*;
import org.eclipse.jnosql.mapping.keyvalue.KeyValueTemplate;

@ApplicationScoped
public class AuthorRepository implements IAuthorRepository {
  private final KeyValueTemplate template;

  @Inject
  public AuthorRepository(KeyValueTemplate template) {
    this.template = template;
  }

  @Override
  public Author save(Author author) {
    return template.put(author);
  }

  @Override
  public void deleteById(String id) {
    template.delete(Author.class, id);
  }

  @Override
  public Optional<Author> findById(String id) {
    return template.get(id, Author.class);
  }

  @Override
  public Iterator<Author> findAllByIds(Set<String> ids) {
    return template.get(ids, Author.class).iterator();
  }
}
