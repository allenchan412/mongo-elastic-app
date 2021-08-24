package com.demo.mongoelastic.service;

import com.demo.mongoelastic.model.mongo.Customer;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer updateCustomer(String id, Customer customer);

    List<Customer> findByFirstName(String firstName);

    List<Customer> findByLastName(String lastName);
}
