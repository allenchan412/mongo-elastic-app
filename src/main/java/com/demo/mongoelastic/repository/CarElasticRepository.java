package com.demo.mongoelastic.repository;

import com.demo.mongoelastic.model.elastic.Car;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarElasticRepository extends ElasticsearchRepository<Car, String> {
    List<Car> findByBrand(String brand);

    List<Car> findByModel(String model);
}
