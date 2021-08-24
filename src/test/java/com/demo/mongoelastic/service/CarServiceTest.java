package com.demo.mongoelastic.service;

import com.demo.mongoelastic.model.elastic.Car;
import com.demo.mongoelastic.model.elastic.ElasticObj;
import com.demo.mongoelastic.repository.CarElasticRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class CarServiceTest {

    private final String CAR_TEST_ID = "1234";
    private Car testCar;
    @Autowired
    private CarService carService;

    @MockBean
    private CarElasticRepository carElasticRepository;

    @BeforeEach
    void setUp() throws Exception{

        testCar = new Car("David", "Ben");
        Field id = ElasticObj.class.getDeclaredField("id");
        id.setAccessible(true);
        id.set(testCar, CAR_TEST_ID);
        given(carElasticRepository.save(any(Car.class))).willAnswer(
                (Answer<Car>) invocation -> {
                    Car car = invocation.getArgument(0);
                    Field idField = ElasticObj.class.getDeclaredField("id");
                    idField.setAccessible(true);
                    idField.set(car, CAR_TEST_ID);
                    return car;
                }
        );
        given(carElasticRepository.findById(CAR_TEST_ID)).willReturn(Optional.of(testCar));
        given(carElasticRepository.findByBrand(any(String.class))).willReturn(List.of(testCar));
        given(carElasticRepository.findByModel(any(String.class))).willReturn(List.of(testCar));
    }

    @Test
    public void createCar_success(){

        Car newCar = carService.create(testCar);
        Assertions.assertEquals(CAR_TEST_ID, newCar.getId());
    }

    @Test
    public void createCar_NullPointerException(){

        Exception exception = assertThrows(RuntimeException.class, () -> {
            carService.create(null);
        });

        String expectedMessage = "Car not valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void updateCar_success(){

        Car updateCar = carService.update(CAR_TEST_ID, testCar);
        Assertions.assertEquals(CAR_TEST_ID, updateCar.getId());
        Assertions.assertEquals(testCar.getBrand(), updateCar.getBrand());
    }

    @Test
    public void updateCar_NullPointerException(){

        Exception exception = assertThrows(RuntimeException.class, () -> {
            carService.update(CAR_TEST_ID, null);
        });

        String expectedMessage = "Id or car not valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void updateCar_RunTimeException(){

        Exception exception = assertThrows(RuntimeException.class, () -> {
            carService.update("3343", testCar);
        });

        String expectedMessage = "Car id:3343 not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void findByBrand_success(){

        List<Car> carList = carService.findByBrand("David");
        Assertions.assertEquals(1, carList.size());
    }

    @Test
    public void findByModel_success(){

        List<Car> carList = carService.findByModel("M");
        Assertions.assertEquals(1, carList.size());
    }
}
