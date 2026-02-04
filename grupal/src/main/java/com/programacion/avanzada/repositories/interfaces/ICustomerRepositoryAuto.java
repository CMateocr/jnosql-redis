package com.programacion.avanzada.repositories.interfaces;

import com.programacion.avanzada.model.Customer;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;

@Repository
public interface ICustomerRepositoryAuto extends CrudRepository<Customer, String> {}
