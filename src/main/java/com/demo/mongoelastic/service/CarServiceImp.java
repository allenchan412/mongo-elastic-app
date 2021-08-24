package com.demo.mongoelastic.service;

import com.demo.mongoelastic.model.elastic.Car;
import com.demo.mongoelastic.repository.CarElasticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CarServiceImp implements CarService {

    @Autowired
    private CarElasticRepository carElasticRepository;

    @Override
    public Car create(Car car) {

        if (Objects.isNull(car)) {
            throw new NullPointerException("Car not valid");
        }
        return carElasticRepository.save(car);
    }

    @Override
    public Car update(String id, Car car) {

        if (Objects.isNull(id) || Objects.isNull(car)) {
            throw new NullPointerException("Id or car not valid");
        }

        Car existCar = carElasticRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Car id:" + id + " not exist");
                });
        existCar.setBrand(car.getBrand());
        existCar.setModel(car.getModel());
        return carElasticRepository.save(existCar);

    }

    @Override
    public List<Car> findByBrand(String brand) {

        if (Objects.isNull(brand)) {
            throw new NullPointerException("Brand is invalid");
        }

        return carElasticRepository.findByBrand(brand);
    }

    @Override
    public List<Car> findByModel(String model) {

        if (Objects.isNull(model)) {
            throw new NullPointerException("Model is invalid");
        }

        return carElasticRepository.findByModel(model);
    }

}
