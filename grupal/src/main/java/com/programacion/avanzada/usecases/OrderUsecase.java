package com.programacion.avanzada.usecases;

import com.programacion.avanzada.dtos.OrderItem;
import com.programacion.avanzada.model.*;
import com.programacion.avanzada.repositories.interfaces.IPurchaseOrderRepository;
import com.programacion.avanzada.services.interfaces.ICatalogService;
import com.programacion.avanzada.services.interfaces.ICustomerService;
import com.programacion.avanzada.services.interfaces.IInventoryService;
import com.programacion.avanzada.shared.AppConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class OrderUsecase {

  private final IPurchaseOrderRepository purchaseOrderRepository;
  private final ICustomerService customerService;
  private final ICatalogService catalogService;
  private final IInventoryService inventoryService;

  @Inject
  public OrderUsecase(
      IPurchaseOrderRepository purchaseOrderRepository,
      ICustomerService customerService,
      ICatalogService catalogService,
      IInventoryService inventoryService) {
    this.purchaseOrderRepository = purchaseOrderRepository;
    this.customerService = customerService;
    this.catalogService = catalogService;
    this.inventoryService = inventoryService;
  }

  public PurchaseOrder buy(String customerId, List<OrderItem> items) {
    Customer client = customerService.getCustomer(customerId);

    List<LineItem> lineItems = new ArrayList<>();
    double total = 0.0;
    int index = 1;

    for (OrderItem request : items) {
      String isbn = request.isbn();
      int amount = request.amount();

      Book book = catalogService.getBook(isbn);

      inventoryService.reduceStock(isbn, amount, book.getTitle());

      double itemTotal = book.getPrice() * amount;
      total += itemTotal;

      LineItem item = LineItem.builder().idx(index++).quantity(amount).bookIsbn(isbn).build();

      lineItems.add(item);
    }

    // ! 3. Persistence: Create and Save the Order
    Long orderId = Math.abs(new Random().nextLong());

    PurchaseOrder order =
        PurchaseOrder.builder()
            .id(AppConstants.buildPurchaseOrderKey(orderId.toString()))
            .customerId(client.getId())
            .placedOn(LocalDateTime.now())
            .status(Status.PLACED)
            .total(total)
            .items(lineItems)
            .build();

    return purchaseOrderRepository.save(order);
  }
}
