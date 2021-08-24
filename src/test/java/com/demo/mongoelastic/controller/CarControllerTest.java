package com.demo.mongoelastic.controller;

import com.demo.mongoelastic.model.elastic.Car;
import com.demo.mongoelastic.model.elastic.ElasticObj;
import com.demo.mongoelastic.repository.CarElasticRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Disabled
public class CarControllerTest {

    private final String CAR_TEST_ID = "1234";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarElasticRepository carElasticRepository;


    @Test
    public void createCar_thenReturnSuccess() throws Exception{

        Car car = new Car("David", "Ben");

        Field id = ElasticObj.class.getDeclaredField("id");
        id.setAccessible(true);
        id.set(car, CAR_TEST_ID);

        mockMvc.perform(post("/car/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isOk());

        Car existCar = carElasticRepository.findById(CAR_TEST_ID).get();
        Assertions.assertEquals(CAR_TEST_ID, existCar.getId());
    }

    @Test
    public void updateCar_thenReturnSuccess() throws Exception{

        String brandName = "BMW";
        Car car = new Car("David", brandName);

        mockMvc.perform(put("/car/{id}", CAR_TEST_ID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isOk());

        Car existCar = carElasticRepository.findById(CAR_TEST_ID).get();
        Assertions.assertEquals(brandName, existCar.getBrand());
    }

    @Test
    public void updateCar_thenReturnBadRequest() throws Exception{

        mockMvc.perform(put("/car/{id}", CAR_TEST_ID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void updateCar_thenReturnInternalServerError() throws Exception{

        Car car = new Car("David", "Ben");

        mockMvc.perform(put("/car/{id}", "0999")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void getByBrand_thenReturnSuccess() throws Exception {

        mockMvc.perform(get("/car/brand/{brand}", "Ben")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getByModel_thenReturnSuccess() throws Exception {

        mockMvc.perform(get("/car/model/{brand}", "David")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
