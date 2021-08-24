package com.demo.mongoelastic.service;

import com.demo.mongoelastic.model.mongo.Customer;
import com.demo.mongoelastic.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {

        if (Objects.isNull(customer)) {
            throw new NullPointerException("Customer not valid");
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(String id, Customer customer) {

        if (Objects.isNull(id) || Objects.isNull(customer)) {
            throw new NullPointerException("Id or customer not valid");
        }

        Customer existCustomer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Customer id:" + id + " not exist");
                });

        existCustomer.setFirstName(customer.getFirstName());
        existCustomer.setLastName(customer.getLastName());
        return customerRepository.save(existCustomer);
    }

    @Override
    public List<Customer> findByFirstName(String firstName) {

        if (Objects.isNull(firstName)) {
            throw new NullPointerException("firstName is invalid");
        }
        return customerRepository.findByFirstName(firstName);
    }

    @Override
    public List<Customer> findByLastName(String lastName) {

        if (Objects.isNull(lastName)) {
            throw new NullPointerException("lastName is invalid");
        }
        return customerRepository.findByLastName(lastName);
    }

}
