package com.programacion.avanzada.services.impl;

import com.programacion.avanzada.model.Author;
import com.programacion.avanzada.model.Book;
import com.programacion.avanzada.repositories.interfaces.IAuthorRepository;
import com.programacion.avanzada.repositories.interfaces.IBookRepository;
import com.programacion.avanzada.services.interfaces.ICatalogService;
import com.programacion.avanzada.services.interfaces.IInventoryService;
import com.programacion.avanzada.shared.AppConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class CatalogService implements ICatalogService {

  private final IBookRepository bookRepository;
  private final IAuthorRepository authorRepository;
  private final IInventoryService inventoryService;

  @Inject
  public CatalogService(
      IBookRepository bookRepository,
      IAuthorRepository authorRepository,
      IInventoryService inventoryService) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.inventoryService = inventoryService;
  }

  @Override
  public void registerAuthor(String id, String name) {
    Author author =
        Author.builder().id(AppConstants.buildAuthorKey(id)).name(name).version(1).build();
    authorRepository.save(author);
  }

  @Override
  public void registerBook(String isbn, String title, Double price, List<String> authorIds) {
    this.validateAuthorsExist(authorIds, title);

    Book book =
        Book.builder()
            .isbn(AppConstants.buildBookKey(isbn))
            .title(title)
            .price(price)
            .authorIds(authorIds)
            .version(1)
            .build();
    bookRepository.save(book);

    inventoryService.createInitialStock(isbn);
  }

  @Override
  public Book getBook(String id) {
    return bookRepository
        .findById(AppConstants.buildBookKey(id))
        .orElseThrow(() -> new RuntimeException("Book not found: " + id));
  }

  private boolean existsBook(String id) {
    return bookRepository.findById(id).isPresent();
  }

  @Override
  public void updateBook(Book book) {
    if (!this.existsBook(book.getIsbn())) {
      throw new RuntimeException("Book not found: " + book.getIsbn());
    }

    bookRepository.save(book);
  }

  @Override
  public void deleteBook(String isbn) {
    getBook(isbn);

    bookRepository.deleteById(AppConstants.buildBookKey(isbn));
  }

  @Override
  public void updateAuthor(Author author) {
    authorRepository
        .findById(author.getId())
        .orElseThrow(() -> new RuntimeException("Author not found: " + author.getId()));

    authorRepository.save(author);
  }

  @Override
  public void deleteAuthor(String id) {
    String key = AppConstants.buildAuthorKey(id);

    authorRepository
        .findById(key)
        .orElseThrow(() -> new RuntimeException("Author not found: " + id));

    authorRepository.deleteById(key);
  }

  private void validateAuthorsExist(List<String> authorIds, String bookTitle) {
    Set<String> uniqueIds = new HashSet<>(authorIds);

    Set<String> redisKeys =
        uniqueIds.stream().map(AppConstants::buildAuthorKey).collect(Collectors.toSet());

    List<Author> foundAuthors = new ArrayList<>();
    authorRepository.findAllByIds(redisKeys).forEachRemaining(foundAuthors::add);

    if (foundAuthors.size() != uniqueIds.size()) {
      throw new RuntimeException("Some authors not found for book: " + bookTitle);
    }
  }
}
