package com.programacion.avanzada.services.interfaces;

import com.programacion.avanzada.model.Author;
import com.programacion.avanzada.model.Book;
import java.util.List;

public interface ICatalogService {
  void registerAuthor(String id, String name);

  void registerBook(String isbn, String title, Double price, List<String> authorIds);

  Book getBook(String isbn);

  void updateBook(Book book);

  void deleteBook(String isbn);

  void deleteAuthor(String id);

  void updateAuthor(Author author);
}
