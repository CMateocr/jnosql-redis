package com.programacion.avanzada.services.interfaces;

import com.programacion.avanzada.dtos.OrderItem;
import com.programacion.avanzada.model.PurchaseOrder;
import java.util.List;

public interface IPurchaseOrderService {
  PurchaseOrder buy(String customerId, List<OrderItem> items);

  void deleteOrder(String orderId);
}
