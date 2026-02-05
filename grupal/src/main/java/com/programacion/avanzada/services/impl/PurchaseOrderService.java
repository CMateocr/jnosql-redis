package com.programacion.avanzada.services.impl;

import com.programacion.avanzada.services.dtos.LineItemDTO;
import com.programacion.avanzada.model.*;
import com.programacion.avanzada.repositories.interfaces.IPurchaseOrderRepository;
import com.programacion.avanzada.services.interfaces.ICatalogService;
import com.programacion.avanzada.services.interfaces.ICustomerService;
import com.programacion.avanzada.services.interfaces.IInventoryService;
import com.programacion.avanzada.services.interfaces.IPurchaseOrderService;
import com.programacion.avanzada.shared.AppConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PurchaseOrderService implements IPurchaseOrderService {

  private final IPurchaseOrderRepository purchaseOrderRepository;
  private final ICustomerService customerService;
  private final ICatalogService catalogService;
  private final IInventoryService inventoryService;

  @Inject
  public PurchaseOrderService(
      IPurchaseOrderRepository purchaseOrderRepository,
      ICustomerService customerService,
      ICatalogService catalogService,
      IInventoryService inventoryService) {
    this.purchaseOrderRepository = purchaseOrderRepository;
    this.customerService = customerService;
    this.catalogService = catalogService;
    this.inventoryService = inventoryService;
  }

  @Override
  public PurchaseOrder buy(String customerId, List<LineItemDTO> items) {
    Customer client = customerService.getCustomer(customerId);

    List<LineItem> lineItems = new ArrayList<>();
    double total = 0.0;
    int id = 0;

    for (LineItemDTO request : items) {

      String isbn = request.isbn();
      int amount = request.amount();

      Book book = catalogService.getBook(isbn);

      inventoryService.reduceStock(isbn, amount, book.getTitle());

      double itemTotal = book.getPrice() * amount;
      total += itemTotal;

      LineItem item = LineItem.builder().idx(id++).quantity(amount).bookIsbn(isbn).build();

      lineItems.add(item);
    }

    String orderId = UUID.randomUUID().toString();

    PurchaseOrder order =
        PurchaseOrder.builder()
            .id(AppConstants.buildPurchaseOrderKey(orderId))
            .customerId(client.getId())
            .placedOn(LocalDateTime.now())
            .status(Status.PLACED)
            .total(total)
            .items(lineItems)
            .build();

    return purchaseOrderRepository.save(order);
  }

  @Override
  public void deleteOrder(String orderId) {
    purchaseOrderRepository.deleteById(orderId);
  }
}
