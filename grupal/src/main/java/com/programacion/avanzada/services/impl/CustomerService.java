package com.programacion.avanzada.services.impl;

import com.programacion.avanzada.model.Customer;
import com.programacion.avanzada.repositories.interfaces.ICustomerRepository;
import com.programacion.avanzada.services.interfaces.ICustomerService;
import com.programacion.avanzada.shared.AppConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CustomerService implements ICustomerService {

  private final ICustomerRepository customerRepository;

  @Inject
  public CustomerService(ICustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public void registerClient(String id, String name, String email) {
    Customer client =
        Customer.builder()
            .id(AppConstants.buildCustomerKey(id))
            .name(name)
            .email(email)
            .version(1)
            .build();

    customerRepository.save(client);
  }

  @Override
  public Customer getCustomer(String id) {
    return customerRepository
        .findById(AppConstants.buildCustomerKey(id))
        .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));
  }

  @Override
  public void updateClient(Customer client) {
    if (!this.exists(client.getId())) {
      throw new RuntimeException("Client not found with ID: " + client.getId());
    }

    customerRepository.save(client);
  }

  public void deleteClient(String id) {
    getCustomer(id);

    String key = AppConstants.buildCustomerKey(id);

    customerRepository.deleteById(key);
  }

  private boolean exists(String id) {
    return customerRepository.findById(id).isPresent();
  }
}
