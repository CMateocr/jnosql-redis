package com.programacion.avanzada.services.impl;

import com.programacion.avanzada.model.Inventory;
import com.programacion.avanzada.repositories.interfaces.IInventoryRepository;
import com.programacion.avanzada.services.interfaces.IInventoryService;
import com.programacion.avanzada.shared.AppConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class InventoryService implements IInventoryService {
  private final IInventoryRepository inventoryRepository;

  @Inject
  public InventoryService(IInventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
  }

  public void createInitialStock(String isbn) {
    Inventory inventory =
        Inventory.builder()
            .id(AppConstants.buildInventoryKey(isbn))
            .bookIsbn(isbn)
            .supplied(0)
            .sold(0)
            .version(1)
            .build();

    inventoryRepository.save(inventory);
  }

  @Override
  public void addToStock(String isbn, int amount) {
    Inventory inventory = getInventory(isbn);
    inventory.setSupplied(inventory.getSupplied() + amount);
    inventoryRepository.save(inventory);
  }

  @Override
  public void reduceStock(String isbn, int amount, String bookTitle) {
    Inventory inventory = this.getInventory(isbn);

    int availableStock = inventory.getSupplied() - inventory.getSold();

    if (availableStock < amount) {
      throw new RuntimeException(
          "Not enough stock for: " + bookTitle + ". Available: " + availableStock);
    }

    inventory.setSold(inventory.getSold() + amount);

    inventoryRepository.save(inventory);
  }

  @Override
  public Inventory getInventory(String isbn) {
    return inventoryRepository
        .findById(AppConstants.buildInventoryKey(isbn))
        .orElseThrow(() -> new RuntimeException("Inventory not found for ISBN: " + isbn));
  }

  @Override
  public void updateInventory(Inventory inventory) {
    this.inventoryRepository
        .findById(inventory.getId())
        .orElseThrow(
            () -> new RuntimeException("Inventory not found for ID: " + inventory.getId()));

    inventoryRepository.save(inventory);
  }

  @Override
  public void deleteInventory(String isbn) {
    getInventory(isbn);

    inventoryRepository.deleteById(AppConstants.buildInventoryKey(isbn));
  }
}
