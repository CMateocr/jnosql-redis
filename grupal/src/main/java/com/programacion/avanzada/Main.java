package com.programacion.avanzada;

import com.programacion.avanzada.services.dtos.LineItemDTO;
import com.programacion.avanzada.model.PurchaseOrder;
import com.programacion.avanzada.services.interfaces.ICatalogService;
import com.programacion.avanzada.services.interfaces.ICustomerService;
import com.programacion.avanzada.services.interfaces.IInventoryService;
import com.programacion.avanzada.services.interfaces.IPurchaseOrderService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import java.util.List;

public class Main {

  private static final String AUTHOR_ID = "100";
  private static final String BOOK_ISBN = "978-1";
  private static final String CUSTOMER_ID = "555";

  public static void main(String[] args) {

    try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

      ICatalogService catalogService = container.select(ICatalogService.class).get();
      IInventoryService inventoryService = container.select(IInventoryService.class).get();
      ICustomerService customerService = container.select(ICustomerService.class).get();
      IPurchaseOrderService orderService = container.select(IPurchaseOrderService.class).get();

      executePurchaseFlow(catalogService, inventoryService, customerService, orderService);

      System.out.println("--- Cleaning up ---");
      //      cleanUpData(catalogService, inventoryService, customerService);

      //      deleteOrder(orderService, "some-order-id-to-delete");
    }
  }

  private static void executePurchaseFlow(
      ICatalogService catalogService,
      IInventoryService inventoryService,
      ICustomerService customerService,
      IPurchaseOrderService orderService) {

    System.out.println("--- Starting Application Flow ---");

    System.out.println("Registering Author");
    catalogService.registerAuthor(AUTHOR_ID, "Andy Hunt");

    System.out.println("Registering jBook");
    catalogService.registerBook(BOOK_ISBN, "The Pragmatic Programmer", 45.00, List.of(AUTHOR_ID));

    System.out.println("Adding Stock");
    inventoryService.addToStock(BOOK_ISBN, 50);

    System.out.println("Registering Customer");
    customerService.registerClient(CUSTOMER_ID, "Juan Calvache", "juan@test.com");

    System.out.println("Processing Purchase");
    List<LineItemDTO> cart = List.of(new LineItemDTO(BOOK_ISBN, 2));

    try {
      PurchaseOrder order = orderService.buy(CUSTOMER_ID, cart);

      System.out.println("--- Purchase Successful ---");
      System.out.println("Order id: " + order.getId());
      System.out.println("Status: " + order.getStatus());
      System.out.println("Total: " + order.getTotal());
      System.out.println("Items count: " + order.getItems().size());

    } catch (Exception e) {
      System.err.println("Purchase Failed: " + e.getMessage());
      e.printStackTrace();
    }

    var inventory = inventoryService.getInventory(BOOK_ISBN);
    System.out.println("--- Inventory Status ---");
    System.out.println("Sold: " + inventory.getSold());
    System.out.println("Remaining: " + (inventory.getSupplied() - inventory.getSold()));
  }

  private static void deleteOrder(IPurchaseOrderService orderService, String id) {
    orderService.deleteOrder(id);
  }

  private static void cleanUpData(
      ICatalogService catalogService,
      IInventoryService inventoryService,
      ICustomerService customerService) {

    try {
      // ! El orden importa unp poco primero inventario, luego libros.

      System.out.println("Deleting Inventory");
      inventoryService.deleteInventory(BOOK_ISBN);

      System.out.println("Deleting Book");
      catalogService.deleteBook(BOOK_ISBN);

      System.out.println("Deleting Author");
      catalogService.deleteAuthor(AUTHOR_ID);

      System.out.println("Deleting Customer...");
      customerService.deleteClient(CUSTOMER_ID);

      System.out.println("--- Data Cleaned Successfully ---");

    } catch (Exception e) {
      System.err.println("Error during cleanup: " + e.getMessage());
    }
  }

  
  public void sayHi() {
    System.out.println("Hi!");
  }
}
