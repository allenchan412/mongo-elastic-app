package com.demo.mongoelastic.service;

import com.demo.mongoelastic.model.mongo.Customer;
import com.demo.mongoelastic.model.mongo.MongoObj;
import com.demo.mongoelastic.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class CustomerServiceTest {

    private final String CUS_TEST_ID = "23232";
    private Customer testCustomer;

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() throws Exception{

        testCustomer = new Customer("David", "Johnson");
        Field id = MongoObj.class.getDeclaredField("id");
        id.setAccessible(true);
        id.set(testCustomer, CUS_TEST_ID);
        given(customerRepository.save(any(Customer.class))).willAnswer(
                (Answer<Customer>) invocation -> {
                    Customer customer = invocation.getArgument(0);
                    Field idField = MongoObj.class.getDeclaredField("id");
                    idField.setAccessible(true);
                    idField.set(customer, CUS_TEST_ID);
                    return customer;
                }
        );
        given(customerRepository.findById(CUS_TEST_ID)).willReturn(Optional.of(testCustomer));
        given(customerRepository.findByLastName(any(String.class))).willReturn(List.of(testCustomer));
        given(customerRepository.findByFirstName(any(String.class))).willReturn(List.of(testCustomer));
    }

    @Test
    public void createCustomer(){

        Customer newCustomer = customerService.createCustomer(testCustomer);
        Assertions.assertEquals(CUS_TEST_ID, newCustomer.getId());
    }

    @Test
    public void createCustomer_NullPointerException(){

        Exception exception = assertThrows(RuntimeException.class, () -> {
            customerService.createCustomer(null);
        });

        String expectedMessage = "Customer not valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void updateCustomer_success(){

        Customer updateCustomer = customerService.updateCustomer(CUS_TEST_ID, testCustomer);
        Assertions.assertEquals(CUS_TEST_ID, updateCustomer.getId());
        Assertions.assertEquals(testCustomer.getFirstName(), updateCustomer.getFirstName());
        Assertions.assertEquals(testCustomer.getLastName(), updateCustomer.getLastName());
    }

    @Test
    public void updateCustomer_NullPointerException(){

        Exception exception = assertThrows(RuntimeException.class, () -> {
            customerService.updateCustomer(CUS_TEST_ID, null);
        });

        String expectedMessage = "Id or customer not valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void updateCustomer_RunTimeException(){

        Exception exception = assertThrows(RuntimeException.class, () -> {
            customerService.updateCustomer("3343", testCustomer);
        });

        String expectedMessage = "Customer id:3343 not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void findByFistName_success(){

        List<Customer> customerList = customerService.findByFirstName("David");
        Assertions.assertEquals(1, customerList.size());
    }

    @Test
    public void findByLastName_success(){

        List<Customer> customerList = customerService.findByLastName("Johnson");
        Assertions.assertEquals(1, customerList.size());
    }

}
