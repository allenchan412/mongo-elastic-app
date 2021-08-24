package com.demo.mongoelastic.service;

import com.demo.mongoelastic.model.elastic.Car;

import java.util.List;

public interface CarService {

    Car create(Car car);

    List<Car> findByBrand(String brand);

    List<Car> findByModel(String model);

    Car update(String id, Car car);
}
