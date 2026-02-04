package com.programacion.avanzada.repositories.impl;

import com.programacion.avanzada.model.Customer;
import com.programacion.avanzada.repositories.interfaces.ICustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import org.eclipse.jnosql.mapping.keyvalue.KeyValueTemplate;

@ApplicationScoped
public class CustomerRepository implements ICustomerRepository {
  private final KeyValueTemplate template;

  @Inject
  public CustomerRepository(KeyValueTemplate template) {
    this.template = template;
  }

  @Override
  public Customer save(Customer customer) {
    return template.put(customer);
  }

  @Override
  public Optional<Customer> findById(String id) {
    return template.get(id, Customer.class);
  }

  @Override
  public void deleteById(String id) {
    template.delete(Customer.class, id);
  }
}
