package com.demo.mongoelastic.controller;

import com.demo.mongoelastic.model.elastic.Car;
import com.demo.mongoelastic.model.mongo.Customer;
import com.demo.mongoelastic.model.mongo.MongoObj;
import com.demo.mongoelastic.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Disabled
public class CustomerControllerTest {

    private final String CUS_TEST_ID = "1234";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void createCustomer_thenReturnSuccess() throws Exception{

        Customer customer = new Customer("David", "Johnson");

        Field id = MongoObj.class.getDeclaredField("id");
        id.setAccessible(true);
        id.set(customer, CUS_TEST_ID);

        mockMvc.perform(post("/customer/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk());

        Customer existCus = customerRepository.findById(CUS_TEST_ID).get();
        Assertions.assertEquals(CUS_TEST_ID, existCus.getId());
    }

    @Test
    public void updateCustomer_thenReturnSuccess() throws Exception{

        String lastName = "Peter";
        Customer cus = new Customer("David", lastName);

        mockMvc.perform(put("/customer/{id}", CUS_TEST_ID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(cus)))
                .andExpect(status().isOk());

        Customer existCus = customerRepository.findById(CUS_TEST_ID).get();
        Assertions.assertEquals(lastName, existCus.getLastName());
    }

    @Test
    public void updateCar_thenReturnBadRequest() throws Exception{

        mockMvc.perform(put("/customer/{id}", CUS_TEST_ID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void updateCar_thenReturnInternalServerError() throws Exception{

        Car car = new Car("David", "Ben");

        mockMvc.perform(put("/customer/{id}", "0999")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void getByFirstName_thenReturnSuccess() throws Exception {

        mockMvc.perform(get("/customer/firstname/{firstName}", "Ben")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getByLastName_thenReturnSuccess() throws Exception {

        mockMvc.perform(get("/customer/lastname/{lastname}", "David")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
