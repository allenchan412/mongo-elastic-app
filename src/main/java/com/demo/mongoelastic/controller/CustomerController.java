package com.demo.mongoelastic.controller;

import com.demo.mongoelastic.model.mongo.Customer;
import com.demo.mongoelastic.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {

        try {
            return ResponseEntity.ok().body(customerService.createCustomer(customer));
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable String id, @RequestBody Customer customer) {

        try {
            return ResponseEntity.ok().body(customerService.updateCustomer(id, customer));
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/firstname/{firstName}")
    public ResponseEntity<List<Customer>> getByFirstName(@PathVariable String firstName) {

        try {
            return ResponseEntity.ok().body(customerService.findByFirstName(firstName));
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/lastname/{lastName}")
    public ResponseEntity<List<Customer>> getByLastName(@PathVariable String lastName) {

        try {
            return ResponseEntity.ok().body(customerService.findByLastName(lastName));
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
