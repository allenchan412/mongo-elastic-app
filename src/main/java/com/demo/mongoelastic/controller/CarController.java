package com.demo.mongoelastic.controller;

import com.demo.mongoelastic.model.elastic.Car;
import com.demo.mongoelastic.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {

        try {
            return ResponseEntity.ok().body(carService.create(car));
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable String id,
                                         @RequestBody Car car) {

        try {
            return ResponseEntity.ok().body(carService.update(id, car));
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Car>> findByBrand(@PathVariable String brand) {

        try {
            return ResponseEntity.ok().body(carService.findByBrand(brand));
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/model/{model}")
    public ResponseEntity<List<Car>> findByModel(@PathVariable String model) {

        try {
            return ResponseEntity.ok().body(carService.findByModel(model));
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
