package com.programacion.avanzada.services.interfaces;

import com.programacion.avanzada.services.dtos.LineItemDTO;
import com.programacion.avanzada.model.PurchaseOrder;
import java.util.List;

public interface IPurchaseOrderService {
  PurchaseOrder buy(String customerId, List<LineItemDTO> items);

  void deleteOrder(String orderId);
}
