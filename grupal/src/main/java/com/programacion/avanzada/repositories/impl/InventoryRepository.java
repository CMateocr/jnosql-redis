package com.programacion.avanzada.repositories.impl;

import com.programacion.avanzada.model.Inventory;
import com.programacion.avanzada.repositories.interfaces.IInventoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import org.eclipse.jnosql.mapping.keyvalue.KeyValueTemplate;

@ApplicationScoped
public class InventoryRepository implements IInventoryRepository {

  private final KeyValueTemplate template;

  @Inject
  public InventoryRepository(KeyValueTemplate template) {
    this.template = template;
  }

  @Override
  public Inventory save(Inventory inventory) {
    return this.template.put(inventory);
  }

  @Override
  public Optional<Inventory> findById(String isbn) {
    return this.template.get(isbn, Inventory.class);
  }

  @Override
  public void deleteById(String isbn) {
    this.template.delete(Inventory.class, isbn);
  }
}
