package com.demo.mongoelastic.repository;

import com.demo.mongoelastic.model.mongo.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    Customer save(Customer customer);

    List<Customer> findByFirstName(String firstName);

    List<Customer> findByLastName(String lastName);
}
