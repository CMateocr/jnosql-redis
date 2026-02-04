package com.programacion.avanzada.repositories.impl;

import com.programacion.avanzada.model.Book;
import com.programacion.avanzada.repositories.interfaces.IBookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import org.eclipse.jnosql.mapping.keyvalue.KeyValueTemplate;

@ApplicationScoped
public class BookRepository implements IBookRepository {
  private final KeyValueTemplate template;

  @Inject
  public BookRepository(KeyValueTemplate template) {
    this.template = template;
  }

  @Override
  public Book save(Book entity) {
    return this.template.put(entity);
  }

  @Override
  public void deleteById(String id) {
    template.delete(Book.class, id);
  }

  @Override
  public Optional<Book> findById(String id) {
    return template.get(id, Book.class);
  }
}
