package com.programacion.avanzada.repositories.impl;

import com.programacion.avanzada.model.PurchaseOrder;
import com.programacion.avanzada.repositories.interfaces.IPurchaseOrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import org.eclipse.jnosql.mapping.keyvalue.KeyValueTemplate;

@ApplicationScoped
public class PurchaseOrderRepository implements IPurchaseOrderRepository {
  private final KeyValueTemplate template;

  @Inject
  public PurchaseOrderRepository(KeyValueTemplate template) {
    this.template = template;
  }

  @Override
  public PurchaseOrder save(PurchaseOrder entity) {
    return this.template.put(entity);
  }

  @Override
  public void deleteById(String id) {
    this.template.delete(PurchaseOrder.class, id);
  }

  @Override
  public Optional<PurchaseOrder> findById(String id) {
    return this.template.get(id, PurchaseOrder.class);
  }
}
