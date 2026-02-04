package com.programacion.avanzada.services.interfaces;

import com.programacion.avanzada.model.Customer;

public interface ICustomerService {
  void registerClient(String id, String name, String email);

  Customer getCustomer(String id);

  void updateClient(Customer client);

  void deleteClient(String id);
}
