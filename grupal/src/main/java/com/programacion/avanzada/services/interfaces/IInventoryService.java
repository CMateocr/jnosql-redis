package com.programacion.avanzada.services.interfaces;

import com.programacion.avanzada.model.Inventory;

public interface IInventoryService {
  void createInitialStock(String isbn);

  void addToStock(String isbn, int amount);

  void reduceStock(String isbn, int amount, String bookTitle);

  Inventory getInventory(String isbn);

  void updateInventory(Inventory inventory);

  void deleteInventory(String isbn);
}
